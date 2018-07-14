package com.android.similarwx.present;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.response.RspBill;
import com.android.similarwx.beans.response.RspCashUser;
import com.android.similarwx.inteface.CashViewInterface;
import com.android.similarwx.model.API;

/**
 * Created by Administrator on 2018/7/14.
 */

public class CashPresent {
    CashViewInterface view;
    public CashPresent(CashViewInterface view){
        this.view=view;
    }
    public void getCashUser(){
        API.getInstance().getCashUser(this);
    }
    public void analyzeRes(RspCashUser rspBill) {
        if (rspBill!=null){
            String result=rspBill.getResult();
            if (result.equals("success")){
                view.refreshCash(rspBill.getData());
            }else {
                Toaster.toastShort(rspBill.getErrorMsg());
            }
        }
    }
}
