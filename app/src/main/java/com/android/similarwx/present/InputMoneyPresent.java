package com.android.similarwx.present;

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
    public InputMoneyPresent(InMoneyViewInterface mView){
        this.mView=mView;
    }
    public void inputMoney(String type,String price){
        String userId= SharePreferenceUtil.getString(AppContext.getContext(), AppConstants.USER_ID,"无");
        API.getInstance().inputMoney(userId,type,price,this);
    }

    public void analyzeInputMoney(RspInMoney transfer) {
        if (transfer!=null){
            String result=transfer.getResult();
            if (result.equals("success")){

            }else {
                Toaster.toastShort(transfer.getErrorMsg());
            }
        }
    }
}
