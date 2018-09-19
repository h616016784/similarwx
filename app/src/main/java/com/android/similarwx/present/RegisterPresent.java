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
import com.android.similarwx.inteface.RegisterViewInterface;
import com.android.similarwx.model.API;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/31.
 */

public class RegisterPresent extends BasePresent {
    RegisterViewInterface registerViewInterface;
    private String password;
    public RegisterPresent(RegisterViewInterface registerViewInterface){
        this.registerViewInterface=registerViewInterface;
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
        API.getInstance().register(account,weixinAccount,email,name,confim,nick,birth,gender,alipay,personalitySignature,verifyCode,this);
    }
    //解析相应体
    public void analyzeRes(RspUser rspUser){
        String result=rspUser.getResult();
        if (result.equals("success")){
            User user=rspUser.getData();
            if (user!=null)
                 saveUser(user);
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
        RequestCallback<LoginInfo> callback=new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                Log.e("onSuccess",param.getAccount()+","+param.getAppKey()+","+param.getToken());

                String accid= (String) SharePreferenceUtil.getObject(AppContext.getContext(),AppConstants.USER_ACCID,"paopaotest1");
                NIMClient.getService(AuthService.class).openLocalCache(accid);
                //云信登录成功后才能更新界面
                registerViewInterface.loginScucces(user);
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
