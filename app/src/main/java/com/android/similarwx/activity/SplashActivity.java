package com.android.similarwx.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.primary.Log;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseActivity;
import com.android.similarwx.beans.User;
import com.android.similarwx.inteface.LoginViewInterface;
import com.android.similarwx.inteface.WxViewInterface;
import com.android.similarwx.present.LoginPresent;
import com.android.similarwx.present.WxPresent;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements LoginViewInterface, WxViewInterface {

    @BindView(R.id.splash_iv)
    ImageView splashIv;

    Animator animator;
    private LoginPresent present;
    private WxPresent presentWx;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        present=new LoginPresent(this);
        presentWx=new WxPresent(this);
        animator  = AnimatorInflater.loadAnimator(SplashActivity.this, R.animator.alpha_splash);
        animator.setTarget(splashIv);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                splashIv.setVisibility(View.VISIBLE);
                animator.start();
            }
        },100);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                String accid;
                String token;

                String unionId;
                try {
                    unionId=SharePreferenceUtil.getString(SplashActivity.this, AppConstants.USER_WX_UNIONID,"");
                    if (TextUtils.isEmpty(unionId)){
                        accid= SharePreferenceUtil.getString(SplashActivity.this, AppConstants.USER_ACCID,"");
                        token=SharePreferenceUtil.getString(SplashActivity.this,AppConstants.USER_TOKEN,"");
                        password= SharePreferenceUtil.getString(SplashActivity.this,AppConstants.USER_LOGIN_PASSWORD,"");
                        if (!TextUtils.isEmpty(accid)&&!TextUtils.isEmpty(password)){
                            present.login(accid,password,"","","");
                        }else {
                            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                        }
                    }else {
                        doServiceLogin(unionId);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("splash login","自动登录异常");
                }
//                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    /**
     * 根据微信的id来进行登录操作
     * @param unionId
     */
    private void doServiceLogin(String unionId) {
        presentWx.WxLogin(unionId);
    }

    /**
     * 保存用户信息
     * @param user
     */
    public void saveUser(User user){
        if (user!=null){
            SharePreferenceUtil.saveSerializableObjectDefault(AppContext.getContext(), AppConstants.USER_OBJECT,user);
        }
        if (user.getAccId()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(), AppConstants.USER_ACCID,user.getAccId());
        if (user.getToken()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_TOKEN,user.getToken());
        else
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_TOKEN,"a170417844a19c6bfebb4ab1a137fc31");
        if (user.getName()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_NICK,user.getName());
        if (user.getEmail()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_EMAIL,user.getEmail());
        if (user.getMobile()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_PHONE,user.getMobile());
        if (user.getWechatAccount()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_WEIXIN,user.getWechatAccount());
        if (user.getGender()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_SEX,user.getGender());
        if (user.getId()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_ID,user.getId());
        if (user.getPaymentPasswd()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_PAYPASSWORD,user.getPaymentPasswd());
        if (user.getPasswd()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_LOGIN_PASSWORD,password);
    }
    private void doUpdateLocalYunxin(User user) {
        Map<UserInfoFieldEnum, Object> fields = new HashMap<>(1);
        fields.put(UserInfoFieldEnum.Name, user.getName());//昵称
        fields.put(UserInfoFieldEnum.AVATAR, user.getIcon());//头像
        fields.put(UserInfoFieldEnum.SIGNATURE, user.getPersonalitySignature());//签名
        fields.put(UserInfoFieldEnum.GENDER, user.getGender());//性别
        fields.put(UserInfoFieldEnum.EMAIL, user.getEmail());//电子邮箱
        fields.put(UserInfoFieldEnum.BIRTHDAY, user.getBirth());//生日
        fields.put(UserInfoFieldEnum.MOBILE, user.getMobile());//手机
        NIMClient.getService(UserService.class).updateUserInfo(fields)
                .setCallback(new RequestCallbackWrapper<Void>() {
                    @Override
                    public void onResult(int code, Void result, Throwable exception) {
                        Log.e("UserService.class","保存本地用户信息");
                    }
                });
    }
    private void doNimLogin(LoginInfo loginInfo, User user) {
        NimUIKit.login(loginInfo, new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {

                NIMClient.getService(AuthService.class).openLocalCache(loginInfo.getAccount());
                //跟新本地用户资料
                doUpdateLocalYunxin(user);
                MainChartrActivity.start(SplashActivity.this);
            }

            @Override
            public void onFailed(int code) {
                if (code == 302 || code == 404) {
                    Toaster.toastShort("登录失败");
                } else {
                    Toaster.toastShort("登录失败");
                }
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            }

            @Override
            public void onException(Throwable exception) {
                Toaster.toastShort("登录异常");
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animator!=null){
            animator.cancel();
        }
    }

    @Override
    public void showErrorMessage(String err) {
        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
    }

    @Override
    public void loginScucces(User user) {
        if (user!=null){
            saveUser(user);

            String accid=user.getAccId();
            String token=user.getToken();
            LoginInfo loginInfo=new LoginInfo(accid,token);

            doNimLogin(loginInfo,user);

        }
    }

    @Override
    public void logoutScucces(User user) {

    }

    @Override
    public void refreshTotalBalance(User user) {

    }

    @Override
    public void refreshWxLogin(User user) {
        if (user!=null){
            saveUser(user);
            MainChartrActivity.start(this);
        }
    }

    @Override
    public void refreshWxUpdate(User user) {

    }
}
