package com.android.similarwx.present;

import android.support.v7.app.AppCompatActivity;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.response.RspInMoney;
import com.android.similarwx.inteface.InMoneyViewInterface;
import com.android.similarwx.model.API;
import com.android.similarwx.utils.SharePreferenceUtil;

/**
 * Created by hanhuailong on 2018/6/19.
 */

public class InputMoneyPresent {

    private InMoneyViewInterface mView;
    AppCompatActivity activity;
    public InputMoneyPresent(InMoneyViewInterface mView,AppCompatActivity activity){
        this.mView=mView;
        this.activity=activity;
    }
    public void inputMoney(String type,String price){
        String userId= SharePreferenceUtil.getString(AppContext.getContext(), AppConstants.USER_ID,"无");
        API.getInstance().inputMoney(activity,userId,type,price,this);
    }

    public void analyzeInputMoney(RspInMoney transfer) {
        if (transfer!=null){
            String result=transfer.getResult();
            if (result.equals("success")){
                mView.refreshInMoney(transfer.getData());
            }else {
                Toaster.toastShort(transfer.getErrorMsg());
            }
        }
    }
}
