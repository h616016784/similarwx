package com.android.similarwx.present;

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

/**
 * Created by Administrator on 2018/3/31.
 */

public class RegisterPresent extends BasePresent {
    RegisterViewInterface registerViewInterface;

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
     * @param code
     * @param confim
     * @param nick
     */
    public void register(String account,String weixinAccount,String email,String name,String password,String code,String confim,String nick){
        if(isEmpty(account,weixinAccount,email,name,password,confim,nick)){
            return;
        }
        API.getInstance().register(account,weixinAccount,email,name,confim,nick,this);
    }
    //解析相应体
    public void analyzeRes(RspUser rspUser){
        String code=rspUser.getErrorCode();
        if (code.equals("0000")){
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
        SharePreferenceUtil.putObject(AppContext.getContext(), AppConstants.USER_ACCID,user.getAccid());
        SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_TOKEN,user.getToken());
        SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_ID,user.getId());
        //云信登录
        LoginInfo loginInfo=new LoginInfo(user.getAccid(),user.getToken());
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
        }else if(password!=confirm){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_psd_confirm));
            return true;
        }
        return false;
    }

}
