package com.android.similarwx.present;

import android.text.TextUtils;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.primary.Log;
import com.android.outbaselibrary.utils.StringUtil;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.User;
import com.android.similarwx.inteface.LoginViewInterface;
import com.android.similarwx.inteface.RegisterViewInterface;
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
    public void login(String name, String password,String code) {
        if(isEmpty(name,password)){
            return;
        }
        User user=new User();
        user.setName(name);
        loginViewInterface.loginScucces(user);
    }

    /**
     * 保存用户信息
     * @param user
     */
    public void saveUser(User user){
        SharePreferenceUtil.putObject(AppContext.getContext(), AppConstants.USER_ACCID,user.getName());
        SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_TOKEN,"d2cc461e543720889cd64f329dfe4852");
        //云信登录
        LoginInfo loginInfo=new LoginInfo(user.getName(),"d2cc461e543720889cd64f329dfe4852");
        doYunXinLogin(loginInfo);
    }

    private void doYunXinLogin(LoginInfo loginInfo) {
        RequestCallback<LoginInfo> callback=new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                Log.e("onSuccess",param.getAccount()+","+param.getAppKey()+","+param.getToken());

                String accid= (String) SharePreferenceUtil.getObject(AppContext.getContext(),AppConstants.USER_ACCID,"paopaotest1");
                NIMClient.getService(AuthService.class).openLocalCache(accid);
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


    private boolean isEmpty(String name, String password) {
        if (TextUtils.isEmpty(name)){
            loginViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_acc_notnull));
            return true;
        }else if (TextUtils.isEmpty(password)){
            loginViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_psd_notnull));
            return true;
        }
        return false;
    }


}
