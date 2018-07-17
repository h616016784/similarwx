package com.android.similarwx.present;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.response.RspInMoney;
import com.android.similarwx.beans.response.RspTransfer;
import com.android.similarwx.inteface.RechargeViewInterface;
import com.android.similarwx.model.API;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.Strings.MD5;

import java.util.UUID;

/**
 * Created by Administrator on 2018/6/17.
 */

public class RechargePresent {
    private RechargeViewInterface mView;
    public RechargePresent(RechargeViewInterface mView){
        this.mView=mView;
    }
    public void transfer(String toUserId,String amount){
        String userId= SharePreferenceUtil.getString(AppContext.getContext(), AppConstants.USER_ID,"无");
        String requestNum= MD5.getStringMD5(UUID.randomUUID().toString());
        API.getInstance().transfer(userId,requestNum,toUserId,amount,this);

    }

    public void analyzeRes(RspTransfer transfer) {
        if (transfer!=null){
            String result=transfer.getResult();
            if (result.equals("success")){
                mView.refreshRecharge(transfer.getData());
            }else {
                Toaster.toastShort(transfer.getErrorMsg());
            }
        }
    }

}
