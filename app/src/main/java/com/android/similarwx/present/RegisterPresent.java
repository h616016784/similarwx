package com.android.similarwx.present;

import android.text.TextUtils;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;
import com.android.similarwx.beans.User;
import com.android.similarwx.inteface.RegisterViewInterface;
import com.android.similarwx.model.API;

/**
 * Created by Administrator on 2018/3/31.
 */

public class RegisterPresent extends BasePresent {
    RegisterViewInterface registerViewInterface;

    public RegisterPresent(RegisterViewInterface registerViewInterface){
        this.registerViewInterface=registerViewInterface;

    }
    public void register(String account,String weixinAccount,String email,String name,String password,String code,String confim,String nick){
        if(isEmpty(account,weixinAccount,email,name,password,confim,nick)){
            return;
        }
        API.getInstance().register(account,weixinAccount,email,name,confim,nick);
        User user=new User();
        registerViewInterface.loginScucces(user);
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
