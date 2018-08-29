package com.android.similarwx.present;

import android.text.TextUtils;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.primary.Log;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.activity.MainChartrActivity;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspUser;
import com.android.similarwx.inteface.LoginViewInterface;
import com.android.similarwx.model.API;
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

/**
 * Created by Administrator on 2018/3/31.
 */

public class LoginPresent extends BasePresent {
    private String password;
    private LoginViewInterface loginViewInterface;
    public LoginPresent(LoginViewInterface loginViewInterface){
        this.loginViewInterface=loginViewInterface;
    }

    /**
     * 登录
     * @param name
     * @param password
     * @param code
     */
    public void login(String name, String password,String weixin,String mobile,String code) {
        this.password=password;
        if(isEmpty(name,password,weixin,mobile)){
            return;
        }
        API.getInstance().login(name,password,weixin,mobile,this);
    }

    public void logout(){
        String userId= (String) SharePreferenceUtil.getObject(AppContext.getContext(),AppConstants.USER_ID,"paopaotest1");
        API.getInstance().logout(userId,this);
    }
    public void getTotalBalance(){
        String userId= (String) SharePreferenceUtil.getObject(AppContext.getContext(),AppConstants.USER_ID,"paopaotest1");
        API.getInstance().getTotalBalance(userId,this);
    }

    public void setInvitationCode(String invitationCode) {
        String userId= (String) SharePreferenceUtil.getObject(AppContext.getContext(),AppConstants.USER_ID,"paopaotest1");
        API.getInstance().setInvitationCode(userId,invitationCode,this);
    }
    /**
     * 保存用户信息
     * @param user
     */
    public void saveUser(User user){
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
        if (!TextUtils.isEmpty(password))
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_LOGIN_PASSWORD,password);
        Map<String,String> map=SharePreferenceUtil.getHashMapData(AppContext.getContext(),AppConstants.USER_MAP_OBJECT);
        if (map==null){
            map=new HashMap<>();
            map.put("1","1");
            SharePreferenceUtil.putHashMapData(AppContext.getContext(),AppConstants.USER_MAP_OBJECT,map);
        }
        //云信登录

        String accid=user.getAccId();
        String token=user.getToken();
//        if (TextUtils.isEmpty(accid))
//            accid="hhltest1";
//        if (TextUtils.isEmpty(accid))
//            token="a170417844a19c6bfebb4ab1a137fc31";
        LoginInfo loginInfo=new LoginInfo(accid,token);
//        doYunXinLogin(loginInfo,user);

        doNimLogin(loginInfo,user);
    }

    private void doNimLogin(LoginInfo loginInfo, User user) {
        NimUIKit.login(loginInfo, new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {

                NIMClient.getService(AuthService.class).openLocalCache(loginInfo.getAccount());

                //跟新本地用户资料
                doUpdateLocalYunxin(user);
                loginViewInterface.loginScucces(user);
//                DemoCache.setAccount(account);
//                saveLoginInfo(account, token);
//
//                // 初始化消息提醒配置
//                initNotificationConfig();

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

    private void doYunXinLogin(LoginInfo loginInfo, final User user) {
        RequestCallback<LoginInfo> callback=new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                Log.e("onSuccess",param.getAccount()+","+param.getAppKey()+","+param.getToken());

                String accid= (String) SharePreferenceUtil.getObject(AppContext.getContext(),AppConstants.USER_ACCID,"paopaotest1");
                NIMClient.getService(AuthService.class).openLocalCache(accid);

                //跟新本地用户资料
                doUpdateLocalYunxin(user);
                loginViewInterface.loginScucces(user);
            }

            @Override
            public void onFailed(int code) {
                Log.e("onFailed","错误码："+code);
            }

            @Override
            public void onException(Throwable exception) {
                Log.e("onException",exception.toString());
            }
        };

        NIMClient.getService(AuthService.class).login(loginInfo).setCallback(callback);
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


    private boolean isEmpty(String name, String password,String weixin,String mobile) {
//        if (TextUtils.isEmpty(mobile)){
//            loginViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_phone_notnull));
//            return true;
//        }else if (TextUtils.isEmpty(password)){
//            loginViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_psd_notnull));
//            return true;
//        }
//        if (TextUtils.isEmpty(name)){
//            loginViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_acc_notnull));
//            return true;
//        }else if (TextUtils.isEmpty(password)){
//            loginViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_psd_notnull));
//            return true;
//        }else if (TextUtils.isEmpty(weixin)){
//            loginViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_weixin_notnull));
//            return true;
//        }else if (TextUtils.isEmpty(mobile)){
//            loginViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_phone_notnull));
//            return true;
//        }
        return false;
    }
    public void analyzeRes(RspUser rspUser) {
        String result=rspUser.getResult();
        if (result.equals("success")){
            if (rspUser.getErrorCode().equals("0000")){
                User user=rspUser.getData();
                if (user!=null)
                    saveUser(user);
                else
                    Toaster.toastShort("数据解析异常");
            }else
                Toaster.toastShort(rspUser.getErrorMsg());
        }else {
            Toaster.toastShort(rspUser.getErrorMsg());
        }
    }
    public void analyzeResLogout(RspUser rspUser) {
        String result=rspUser.getResult();
        if (result.equals("success")){
            clearUserInfo();
            loginViewInterface.logoutScucces(rspUser.getData());
        }else {
            Toaster.toastShort(rspUser.getErrorMsg());
        }
    }

    public void analyzeTotalBalance(RspUser rspUser) {
        String result=rspUser.getResult();
        if (result.equals("success")){
            loginViewInterface.refreshTotalBalance(rspUser.getData());
        }else {
            Toaster.toastShort(rspUser.getErrorMsg());
        }
    }

    private void clearUserInfo() {
            SharePreferenceUtil.saveSerializableObjectDefault(AppContext.getContext(),AppConstants.USER_OBJECT,null);
            SharePreferenceUtil.putObject(AppContext.getContext(), AppConstants.USER_ACCID,"");
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_TOKEN,"");
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_TOKEN,"");
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_NICK,"");
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_EMAIL,"");
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_PHONE,"");
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_WEIXIN,"");
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_SEX,"");
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_ID,"");
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_PAYPASSWORD,"");
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_LOGIN_PASSWORD,"");
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_WX_UNIONID,"");
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_MAP_OBJECT,"");
    }
}
