package com.android.similarwx.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseActivity;
import com.android.similarwx.base.BaseDialog;
import com.android.similarwx.beans.User;
import com.android.similarwx.config.UserPreferences;
import com.android.similarwx.fragment.AddGroupFragment;
import com.android.similarwx.fragment.DealFragment;
import com.android.similarwx.fragment.PhoneVerifyFragment;
import com.android.similarwx.fragment.RegistFragment;
import com.android.similarwx.fragment.SetPayPasswordFragment;
import com.android.similarwx.inteface.LoginViewInterface;
import com.android.similarwx.present.LoginPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.WXUtil;
import com.android.similarwx.widget.dialog.EditDialogBuilder;
import com.android.similarwx.wxapi.WXEntryActivity;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.netease.nim.uikit.business.team.DemoCache;
import com.netease.nim.uikit.support.permission.MPermission;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionDenied;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionGranted;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionNeverAskAgain;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity implements LoginViewInterface {
    private final int BASIC_PERMISSION_REQUEST_CODE = 110;
    private IWXAPI api;

    @BindView(R.id.regist)
    TextView regist;
    @BindView(R.id.login_account)
    EditText loginAccount;
    @BindView(R.id.login_mobile_et)
    EditText loginMobileEt;
    @BindView(R.id.login_weixin_et)
    EditText loginWeixinEt;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_error)
    TextView loginError;
    @BindView(R.id.login_login)
    Button loginLogin;
    @BindView(R.id.login_forget_password)
    TextView loginForgetPassword;
    @BindView(R.id.login_deal_tv)
    TextView loginDealTv;
    @BindView(R.id.login_wx_iv)
    ImageView loginWxIv;
    private LoginPresent loginPresent;
    BaseDialog dialog;
    public static void start(Activity context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
    /**
     * 基本权限管理
     */

    private final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.VIBRATE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private void requestBasicPermission() {
        MPermission.with(LoginActivity.this)
                .setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(BASIC_PERMISSIONS)
                .request();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化butterKnife
        ButterKnife.bind(this);
//        requestBasicPermission();
        loginPresent = new LoginPresent(this,this);
        initPerssion();

        api= WXUtil.getInstance(this).getApi();
        //防止抖动
        RxView.clicks(loginLogin).throttleFirst(1,TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Object value) {
                        loginError.setVisibility(View.GONE);
                        String name = loginAccount.getText().toString();
                        String mobile = loginMobileEt.getText().toString();
                        String password = loginPassword.getText().toString();
                        String weixin = loginWeixinEt.getText().toString();
                        loginPresent.login(mobile, password, weixin,"",null);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Toaster.toastShort(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete","点击了！");
                    }
                });
        registerReceiver(mFinishReceiver, new IntentFilter(ARGUMENT_EXTRA_ANIMATION_LOGIN));
        Intent intent = new Intent();
        intent.setAction(ACTION_FINISH);
        sendBroadcast(intent);
    }

    @Override
    public boolean getRegisterAble() {
        return false;
    }

    private void initPerssion() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .requestEach(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.VIBRATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE)
                .subscribe(permission -> { // will emit 2 Permission objects
                    if (permission.granted) {
                        // `permission.name` is granted !
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // Denied permission without ask never again
                    } else {
                        // Denied permission with ask never again
                        // Need to go to the settings
                    }
                });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
//        Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
    }

    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    @OnMPermissionNeverAskAgain(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.login, R.id.regist, R.id.login_forget_password, R.id.login_wx_iv,R.id.login_deal_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login://

                break;
            case R.id.regist://注册
                FragmentUtils.navigateToNormalActivity(this, new RegistFragment(), null);
                break;
            case R.id.login_forget_password://忘记密码

                Bundle bundle=new Bundle();
                bundle.putString(AppConstants.TRANSFER_PASSWORD_TYPE, SetPayPasswordFragment.LOG_PSD);
                bundle.putInt(SetPayPasswordFragment.MOBILE,1);
                FragmentUtils.navigateToNormalActivity(this, new PhoneVerifyFragment(), bundle);
                break;
            case R.id.login_wx_iv://微信登录
                sendLoginWx("weixin");
                break;
            case R.id.login_login://登录
//                Log.e("消息信息","文本："+MsgTypeEnum.text.getValue()+"图片："+MsgTypeEnum.image.getValue()+"视频："+ MsgTypeEnum.audio.getValue()
//                        +"通知："+MsgTypeEnum.notification.getValue()+"tip:"+MsgTypeEnum.tip.getValue()+"自定义"+MsgTypeEnum.custom.getValue());
//                loginError.setVisibility(View.GONE);
//                String name = loginAccount.getText().toString();
//                String mobile = loginMobileEt.getText().toString();
//                String password = loginPassword.getText().toString();
//                String weixin = loginWeixinEt.getText().toString();
//                loginPresent.login(name, password, weixin,mobile,null);
                break;
            case R.id.login_deal_tv:
                FragmentUtils.navigateToNormalActivity(this, new DealFragment(), null);
                break;
        }
    }

    @Override
    public void loginScucces(User user) {
        //判断是否有邀请码
        String inviter=user.getInviter();
        if (TextUtils.isEmpty(inviter)){
            dialog=new  EditDialogBuilder(this)
                    .setMessage("请输入推荐码")
                    .setConfirmButtonNoDismiss(new EditDialogBuilder.ButtonClicker() {
                        @Override
                        public void onButtonClick(String str) {
                            if (TextUtils.isEmpty(str))
                                Toaster.toastShort("推荐码不能为空！");
                            else
                                doInputInviter(user.getId(),str);
                        }
                    })
                    .create();
            dialog.show();
        }else {
            loginPresent.saveUser(user);
            //之后跳转界面
//            startActivity(new Intent(this, MainChartrActivity.class));
        }
    }



    private void doInputInviter(String userId,String invitationCode) {
        loginPresent.setInvitationCode(userId,invitationCode);
    }

    @Override
    public void logoutScucces(User user) {

    }

    @Override
    public void refreshTotalBalance(User user) {
        if (user!=null){
            loginPresent.saveUser(user);
        }
    }

    @Override
    public void refreshDoYunxinLocal(User user) {
        if (dialog!=null)
            dialog.dismiss();
        //之后跳转界面
        startActivity(new Intent(this, MainChartrActivity.class));
    }

    private void sendLoginWx(String text){
        final SendAuth.Req req=new SendAuth.Req();
        req.scope="snsapi_userinfo";
        req.state="paopao_get_code";
        api.sendReq(req);
    }
    @Override
    public void showErrorMessage(String err) {
        loginError.setVisibility(View.VISIBLE);
        loginError.setText(err);
    }

    @Override
    public void hideKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideKeyboard();
    }
}
