package com.android.similarwx.present;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.primary.Log;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.activity.MainChartrActivity;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspUser;
import com.android.similarwx.config.UserPreferences;
import com.android.similarwx.inteface.LoginViewInterface;
import com.android.similarwx.misdk.helper.UserUpdateHelper;
import com.android.similarwx.model.API;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.team.DemoCache;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
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
    private static final String TAG = LoginPresent.class.getSimpleName();
    private String password;
    private LoginViewInterface loginViewInterface;
    private AppCompatActivity activity;
    public LoginPresent(LoginViewInterface loginViewInterface,AppCompatActivity activity){
        this.loginViewInterface=loginViewInterface;
        this.activity=activity;
    }

    /**
     * 登录
     * @param name
     * @param password
     * @param code
     */
    public void login(String name, String password, String weixin, String mobile, String code) {
        this.password=password;
        if(isEmpty(name,password,weixin,mobile)){
            return;
        }
        API.getInstance().login(activity,name,password,weixin,mobile,this);
    }

    public void logout(){
        String userId= (String) SharePreferenceUtil.getObject(AppContext.getContext(),AppConstants.USER_ID,"");
        API.getInstance().logout(activity,userId,this);
    }
    public void getTotalBalance(){
        String userId= (String) SharePreferenceUtil.getObject(AppContext.getContext(),AppConstants.USER_ID,"");
        API.getInstance().getTotalBalance(activity,userId,this);
    }

    public void setInvitationCode(String userId,String invitationCode) {
        API.getInstance().setInvitationCode(activity,userId,invitationCode,this);
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
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_TOKEN,"");
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

                //跟新本地用户资料并跳
                doUpdateLocalYunxin(user);

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
    private void initNotificationConfig() {
        // 初始化消息提醒
        NIMClient.toggleNotification(true);

        // 加载状态栏配置
        StatusBarNotificationConfig statusBarNotificationConfig = UserPreferences.getStatusConfig();
        if (statusBarNotificationConfig == null) {
            statusBarNotificationConfig = DemoCache.getNotificationConfig();
            UserPreferences.setStatusConfig(statusBarNotificationConfig);
        }
        // 更新配置
        NIMClient.updateStatusBarNotificationConfig(statusBarNotificationConfig);
    }
    private void doYunXinLogin(LoginInfo loginInfo, final User user) {
        RequestCallback<LoginInfo> callback=new RequestCallback<LoginInfo>() {

            @Override
            public void onSuccess(LoginInfo param) {
                Log.e("onSuccess",param.getAccount()+","+param.getAppKey()+","+param.getToken());

                String accid= (String) SharePreferenceUtil.getObject(AppContext.getContext(),AppConstants.USER_ACCID,null);
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
        if (!TextUtils.isEmpty(user.getName()))
            fields.put(UserInfoFieldEnum.Name, user.getName());//昵称
        if (!TextUtils.isEmpty(user.getIcon()))
            fields.put(UserInfoFieldEnum.AVATAR, user.getIcon());//头像
        if (!TextUtils.isEmpty(user.getPersonalitySignature()))
            fields.put(UserInfoFieldEnum.SIGNATURE, user.getPersonalitySignature());//签名
        if (!TextUtils.isEmpty(user.getGender()))
            fields.put(UserInfoFieldEnum.GENDER, Integer.parseInt(user.getGender()));//性别
        if (!TextUtils.isEmpty(user.getEmail()))
            fields.put(UserInfoFieldEnum.EMAIL, user.getEmail());//电子邮箱

        if (!TextUtils.isEmpty(user.getBirth()))
            fields.put(UserInfoFieldEnum.BIRTHDAY, user.getBirth());//生日
        else
            fields.put(UserInfoFieldEnum.BIRTHDAY, "1988-10-01");//生日
        if (!TextUtils.isEmpty(user.getMobile()))
            fields.put(UserInfoFieldEnum.MOBILE, user.getMobile());//手机

        NIMClient.getService(UserService.class).updateUserInfo(fields).setCallback(new RequestCallbackWrapper<Void>() {
            @Override
            public void onResult(int code, Void result, Throwable exception) {

                if (code == ResponseCode.RES_SUCCESS) {
                    LogUtil.i(TAG, "update userInfo success, update fields count=" + fields.size());
                } else {
                    if (exception != null) {
                        Toast.makeText(DemoCache.getContext(), R.string.user_info_update_failed, Toast.LENGTH_SHORT).show();
                        LogUtil.i(TAG, "update userInfo failed, exception=" + exception.getMessage());
                    }
                }
                DemoCache.setAccount(user.getAccId());
//                saveLoginInfo(account, token);
//
                // 初始化消息提醒配置
                initNotificationConfig();
                loginViewInterface.refreshDoYunxinLocal(user);
            }
        });
//        NIMClient.getService(UserService.class).updateUserInfo(fields)
//                .setCallback(new RequestCallbackWrapper<Void>() {
//                    @Override
//                    public void onResult(int code, Void result, Throwable exception) {
//                        Log.e("UserService.class","保存本地用户信息");
//                        DemoCache.setAccount(user.getAccId());
////                saveLoginInfo(account, token);
////
//                        // 初始化消息提醒配置
//                        initNotificationConfig();
//                        loginViewInterface.refreshDoYunxinLocal(user);
//                    }
//                });
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
                    loginViewInterface.loginScucces(user);
                else
                    Toaster.toastShort("数据解析异常");
            }else{
                Toaster.toastShort(rspUser.getErrorMsg());
                loginViewInterface.showErrorMessage(rspUser.getErrorMsg());
            }

        }else {
            Toaster.toastShort(rspUser.getErrorMsg());
            loginViewInterface.showErrorMessage(rspUser.getErrorCode());
        }
    }
    public void analyzeResLogout(RspUser rspUser) {
        String result=rspUser.getResult();
        if (result.equals("success")){
            if (rspUser.getErrorCode().equals("0000")){
                clearUserInfo();
                loginViewInterface.logoutScucces(rspUser.getData());
            }else
                Toaster.toastShort(rspUser.getErrorMsg());
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
    public void analyzeInvitationCode(RspUser rspUser) {
        String result=rspUser.getResult();
        if (result.equals("success")){
            if (rspUser.getErrorCode().equals("0000")){
                loginViewInterface.refreshTotalBalance(rspUser.getData());
            }else
                Toaster.toastShort(rspUser.getErrorMsg());
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
