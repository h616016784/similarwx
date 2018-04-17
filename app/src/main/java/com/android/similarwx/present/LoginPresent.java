package com.android.similarwx.present;

import android.text.TextUtils;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.SharedPreferencesUtil;
import com.android.outbaselibrary.utils.StringUtil;
import com.android.similarwx.R;
import com.android.similarwx.beans.User;
import com.android.similarwx.inteface.LoginViewInterface;
import com.android.similarwx.inteface.RegisterViewInterface;

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
        loginViewInterface.loginScucces(user);
    }

    /**
     * 保存用户信息
     * @param user
     */
    public void saveUser(User user){
        SharedPreferencesUtil.put(AppContext.getContext(),"account","");
        SharedPreferencesUtil.put(AppContext.getContext(),"token","");
        SharedPreferencesUtil.put(AppContext.getContext(),"appKey","");
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
