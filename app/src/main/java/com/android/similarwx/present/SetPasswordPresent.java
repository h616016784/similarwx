package com.android.similarwx.present;

import com.android.similarwx.inteface.SetPasswordViewInterface;
import com.android.similarwx.model.API;

/**
 * Created by hanhuailong on 2018/7/6.
 */

public class SetPasswordPresent {
    SetPasswordViewInterface mView;
    public SetPasswordPresent(SetPasswordViewInterface view){
        this.mView=view;
    }
    public void setPassword(String userId,String paymentPasswd,String passwdStr){
        API.getInstance().setPaymentPasswd(userId,paymentPasswd,passwdStr,this);
    }
}
