package com.android.similarwx.present;

import android.text.TextUtils;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.primary.Log;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspUser;
import com.android.similarwx.inteface.LoginViewInterface;
import com.android.similarwx.model.API;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

/**
 * Created by Administrator on 2018/3/31.
 */

public class LoginPresent extends BasePresent {
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
        if(isEmpty(name,password,weixin,mobile)){
            return;
        }
        API.getInstance().login(name,password,weixin,mobile,this);
    }

    /**
     * 保存用户信息
     * @param user
     */
    public void saveUser(User user){
        if (user.getAccId()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(), AppConstants.USER_ACCID,user.getAccId());
        if (user.getToken()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_TOKEN,user.getToken());
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


    private boolean isEmpty(String name, String password,String weixin,String mobile) {
        if (TextUtils.isEmpty(name)){
            loginViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_acc_notnull));
            return true;
        }else if (TextUtils.isEmpty(password)){
            loginViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_psd_notnull));
            return true;
        }else if (TextUtils.isEmpty(weixin)){
            loginViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_weixin_notnull));
            return true;
        }else if (TextUtils.isEmpty(mobile)){
            loginViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_phone_notnull));
            return true;
        }
        return false;
    }
    public void analyzeRes(RspUser rspUser) {
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
}
