package com.android.similarwx.present;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.primary.Log;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspUser;
import com.android.similarwx.config.UserPreferences;
import com.android.similarwx.inteface.RegisterViewInterface;
import com.android.similarwx.model.API;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.team.DemoCache;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
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

public class RegisterPresent extends BasePresent {
    RegisterViewInterface registerViewInterface;
    AppCompatActivity activity;
    private String password;
    public RegisterPresent(RegisterViewInterface registerViewInterface,AppCompatActivity activity){
        this.registerViewInterface=registerViewInterface;
        this.activity=activity;
    }

    /**
     * 注册用户
     * @param account
     * @param weixinAccount
     * @param email
     * @param name
     * @param password
     * @param confim
     * @param nick
     */
    public void register(String account,String weixinAccount,String email,String name,String password,String confim,String nick,String birth,String gender,String alipay,String personalitySignature,String verifyCode){
        this.password=password;
        if(isEmpty(account,name,password,confim,nick)){
            return;
        }
        API.getInstance().register(activity,account,weixinAccount,email,name,confim,nick,birth,gender,alipay,personalitySignature,verifyCode,this);
    }
    //解析相应体
    public void analyzeRes(RspUser rspUser){
        String result=rspUser.getResult();
        if (result.equals("success")){
            User user=rspUser.getData();
            if (user!=null)
                registerViewInterface.loginScucces(user);
//                 saveUser(user);
            else
                Toaster.toastShort("数据解析异常");
        }else {
            Toaster.toastShort(rspUser.getErrorMsg());
        }
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
                        DemoCache.setAccount(user.getAccId());
                        // 初始化消息提醒配置
                        initNotificationConfig();
                        registerViewInterface.loginScucces(user);
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
    public boolean isEmpty(String name) {
        if (TextUtils.isEmpty(name)) {
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_acc_notnull));
            return true;
        }
        return false;
    }
    private boolean isEmpty(String account,String weixinAccount,String email,String name, String password,String confirm,String nick) {
        if (TextUtils.isEmpty(name)){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_phone_notnull));
            return true;
        }else if (TextUtils.isEmpty(nick)){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_nick_notnull));
            return true;
        }else if (TextUtils.isEmpty(account)){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_psd_notnull));
            return true;
        }else if (TextUtils.isEmpty(weixinAccount)){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_weixin_notnull));
            return true;
        }else if (TextUtils.isEmpty(email)){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_email_notnull));
            return true;
        }else if (TextUtils.isEmpty(password)){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_psd_notnull));
            return true;
        }else if (TextUtils.isEmpty(confirm)){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_confirm_notnull));
            return true;
        }else if(!password.equals(confirm)){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_psd_confirm));
            return true;
        }
        return false;
    }
    private boolean isEmpty(String account,String name, String password,String confirm,String nick) {
        if (TextUtils.isEmpty(name)){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_phone_notnull));
            return true;
        }else if (TextUtils.isEmpty(nick)){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_nick_notnull));
            return true;
        }else if (TextUtils.isEmpty(account)){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_acc_notnull));
            return true;
        }else if (TextUtils.isEmpty(password)){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_psd_notnull));
            return true;
        }else if (TextUtils.isEmpty(confirm)){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_confirm_notnull));
            return true;
        }else if(!password.equals(confirm)){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_psd_confirm));
            return true;
        }
        return false;
    }

}
