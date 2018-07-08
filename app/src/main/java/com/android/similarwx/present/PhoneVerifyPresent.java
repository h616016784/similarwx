package com.android.similarwx.present;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.response.BaseResponse;
import com.android.similarwx.inteface.PhoneVerifyViewInterface;
import com.android.similarwx.model.API;

/**
 * Created by Administrator on 2018/7/8.
 */

public class PhoneVerifyPresent {
    private PhoneVerifyViewInterface mView;
    public PhoneVerifyPresent(PhoneVerifyViewInterface mView){
        this.mView=mView;
    }
    public void getMobileVerifyCode(String mobile){
        API.getInstance().getMobileVerifyCode(mobile,this);
    }
    public void analyzeRes(BaseResponse baseResponse) {
        if (baseResponse!=null){
            String result=baseResponse.getResult();
            if (result.equals("success")){
                if(baseResponse.getErrorCode().equals("0000"))
                    mView.refreshGettMobileVerifyCode();
                else
                    Toaster.toastShort(baseResponse.getErrorMsg());
            }else {
                Toaster.toastShort(baseResponse.getErrorMsg());
            }
        }
    }
}
