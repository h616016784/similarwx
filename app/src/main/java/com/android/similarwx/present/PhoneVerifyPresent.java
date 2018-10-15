package com.android.similarwx.present;

import android.support.v7.app.AppCompatActivity;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.response.BaseResponse;
import com.android.similarwx.beans.response.VerifyCodeResponse;
import com.android.similarwx.inteface.PhoneVerifyViewInterface;
import com.android.similarwx.model.API;

/**
 * Created by Administrator on 2018/7/8.
 */

public class PhoneVerifyPresent {
    private PhoneVerifyViewInterface mView;
    AppCompatActivity activity;
    public PhoneVerifyPresent(PhoneVerifyViewInterface mView,AppCompatActivity activity){
        this.mView=mView;
        this.activity=activity;
    }
    public void getMobileVerifyCode(String mobile){
        API.getInstance().getMobileVerifyCode(activity,mobile,this);
    }

    public void analyzeRes(VerifyCodeResponse baseResponse) {
        if (baseResponse!=null){
            String result=baseResponse.getResult();
            if (result.equals("success")){
                if(baseResponse.getErrorCode().equals("0000"))
                    mView.refreshGettMobileVerifyCode(baseResponse.getData());
                else
                    Toaster.toastShort(baseResponse.getErrorMsg());
            }else {
                Toaster.toastShort(baseResponse.getErrorMsg());
            }
        }
    }
}
