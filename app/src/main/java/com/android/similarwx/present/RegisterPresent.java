package com.android.similarwx.present;

import android.text.TextUtils;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;
import com.android.similarwx.beans.User;
import com.android.similarwx.inteface.RegisterViewInterface;

/**
 * Created by Administrator on 2018/3/31.
 */

public class RegisterPresent extends BasePresent {
    RegisterViewInterface registerViewInterface;

    public RegisterPresent(RegisterViewInterface registerViewInterface){
        this.registerViewInterface=registerViewInterface;

    }
    public void register(String name,String password,String code,String confim){
        if(isEmpty(name,password,code,confim)){
            return;
        }
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
    private boolean isEmpty(String name, String password, String code,String confirm) {
        if (TextUtils.isEmpty(name)){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_acc_notnull));
            return true;
        }else if (TextUtils.isEmpty(password)){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_psd_notnull));
            return true;
        }else if(TextUtils.isEmpty(code)){
            registerViewInterface.showErrorMessage(AppContext.getContext().getString(R.string.login_error_code_notnull));
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
