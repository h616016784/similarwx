package com.android.similarwx.present;

import android.support.v7.app.AppCompatActivity;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspGroup;
import com.android.similarwx.beans.response.RspSetPassword;
import com.android.similarwx.inteface.SetPasswordViewInterface;
import com.android.similarwx.model.API;

import java.util.List;

/**
 * Created by hanhuailong on 2018/7/6.
 */

public class SetPasswordPresent {
    SetPasswordViewInterface mView;
    AppCompatActivity activity;
    public SetPasswordPresent(SetPasswordViewInterface view,AppCompatActivity activity){
        this.mView=view;
        this.activity=activity;
    }
    public void setPassword(String mobile,String userId,String paymentPasswd,String passwdStr,String verifyCode){
        API.getInstance().setPaymentPasswd(activity,mobile,userId,paymentPasswd,passwdStr,verifyCode,this);
    }

    public void analyzeRes(RspSetPassword rspGroup) {
        if (rspGroup!=null){
            String result=rspGroup.getResult();
            if (result.equals("success")){
                if (rspGroup.getErrorCode().equals("0000")){
                    mView.refreshSetPassword(rspGroup.getData());
                }else {
                    Toaster.toastShort(rspGroup.getErrorMsg());
                }
            }else {
                Toaster.toastShort(rspGroup.getErrorMsg());
            }
        }
    }
}
