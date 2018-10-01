package com.android.similarwx.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.primary.Log;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.activity.MainChartrActivity;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseDialog;
import com.android.similarwx.beans.AccToken;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.UserInfoWX;
import com.android.similarwx.inteface.LoginViewInterface;
import com.android.similarwx.inteface.WxViewInterface;
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.API;
import com.android.similarwx.model.WXAPI;
import com.android.similarwx.present.LoginPresent;
import com.android.similarwx.present.WxPresent;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.widget.dialog.EditDialogBuilder;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.team.DemoCache;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanhuailong on 2018/7/18.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler, WxViewInterface, LoginViewInterface {

    private IWXAPI api;
    private WxPresent present;
    private UserInfoWX userInfoWXTemp;

    private String unionId;
    private LoginPresent loginPresent;
    BaseDialog dialog;
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api =  WXAPIFactory.createWXAPI(this, AppConstants.WX_APP_ID,false);
        present=new WxPresent(this);
        loginPresent = new LoginPresent(this);
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
        int result = 0;
    }

    @Override
    public void onResp(BaseResp baseResp) {
        int result = 0;
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK://成功
                result = R.string.errcode_success;
                if (baseResp.getType()==ConstantsAPI.COMMAND_SENDAUTH){
                    SendAuth.Resp resp= (SendAuth.Resp) baseResp;
                    //获取acctoken
                    WXAPI.getInstance().accessToken(AppConstants.WX_APP_ID, AppConstants.WX_APP_SECRET, resp.code, "authorization_code", new YCallBack<AccToken>() {
                        @Override
                        public void callBack(AccToken accToken) {
                            if (accToken!=null){
                                WXAPI.getInstance().userInfoWX(accToken.getAccess_token(), accToken.getOpenid(), new YCallBack<UserInfoWX>() {
                                    @Override
                                    public void callBack(UserInfoWX userInfoWX) {
                                        userInfoWXTemp=userInfoWX;
                                        String unionId=userInfoWX.getUnionid();
                                        doServiceLogin(unionId);
                                    }
                                });
                            }
                        }
                    });
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }

        finish();
    }

    /**
     * 根据微信的id来进行登录操作
     * @param unionId
     */
    private void doServiceLogin(String unionId) {
        this.unionId=unionId;
        present.WxLogin(unionId);
    }

    /**
     * 保存用户信息
     * @param user
     */
    public void saveUser(User user){
        if (user!=null){
            SharePreferenceUtil.saveSerializableObjectDefault(AppContext.getContext(), AppConstants.USER_OBJECT,user);
            if (!TextUtils.isEmpty(unionId))
                SharePreferenceUtil.putObject(AppContext.getContext(), AppConstants.USER_WX_UNIONID,unionId);
        }
        if (user!=null){
            SharePreferenceUtil.saveSerializableObjectDefault(AppContext.getContext(),AppConstants.USER_OBJECT,user);
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

        Map<String,String> map=SharePreferenceUtil.getHashMapData(AppContext.getContext(),AppConstants.USER_MAP_OBJECT);
        if (map==null){
            map=new HashMap<>();
            map.put("1","1");
            SharePreferenceUtil.putHashMapData(AppContext.getContext(),AppConstants.USER_MAP_OBJECT,map);
        }
        //云信登录
        LoginInfo loginInfo=new LoginInfo(user.getAccId(),user.getToken());
        doYunXinLogin(loginInfo,user);
    }
    private void doYunXinLogin(LoginInfo loginInfo, final User user) {

        NimUIKit.login(loginInfo, new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {

                NIMClient.getService(AuthService.class).openLocalCache(loginInfo.getAccount());

                //跟新本地用户资料
                doUpdateLocalYunxin(user);
                DemoCache.setAccount(user.getAccId());
            }

            @Override
            public void onFailed(int code) {
                if (code == 302 || code == 404) {
                    Toaster.toastShort("登录失败");
                } else {
                    Toaster.toastShort("登录失败");
                }
            }

            @Override
            public void onException(Throwable exception) {
                Toaster.toastShort("登录异常");
            }
        });
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
    private void doInputInviter(String userId,String invitationCode) {
        loginPresent.setInvitationCode(userId,invitationCode);
    }
    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void refreshWxLogin(User user) {
        doNext(user);
    }

    private void doNext(User user) {
        if (user!=null){
            String id=user.getId();
            API.getInstance().updateUserByWX(id,userInfoWXTemp.getNickname(), userInfoWXTemp.getHeadimgurl(), userInfoWXTemp.getSex() + "", new YCallBack<User>() {

                @Override
                public void callBack(User user) {
                    //判断是否有邀请码
                    String inviter=user.getInviter();
                    if (TextUtils.isEmpty(inviter)){
                        dialog=new EditDialogBuilder(WXEntryActivity.this)
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
                        saveUser(user);
                        if (dialog!=null)
                            dialog.dismiss();
                        //之后跳转界面
                        startActivity(new Intent(WXEntryActivity.this, MainChartrActivity.class));
                    }
                }
            });
        }
    }

    @Override
    public void refreshWxUpdate(User user) {
        doNext(user);
    }
    public void finishApp(){
        while (!AppContext.getActivitiesStack().isEmpty()){
            AppContext.getActivitiesStack().pop().finish();
        }
    }

    @Override
    public void loginScucces(User user) {

    }

    @Override
    public void logoutScucces(User user) {

    }

    @Override
    public void refreshTotalBalance(User user) {
        if (user!=null){
            saveUser(user);
            if (dialog!=null)
                dialog.dismiss();
            //之后跳转界面
            startActivity(new Intent(this, MainChartrActivity.class));
        }
    }

    @Override
    public void refreshDoYunxinLocal(User user) {

    }
}
