package com.android.similarwx.present;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.response.RspRed;
import com.android.similarwx.beans.response.RspSendRed;
import com.android.similarwx.inteface.MiViewInterface;
import com.android.similarwx.model.API;

import java.util.List;

/**
 * Created by Administrator on 2018/4/1.
 */

public class MIPresent extends BasePresent {
    private MiViewInterface mView;
    public MIPresent(MiViewInterface view){
        this.mView=view;
    }
    public void sendRed(SendRed sendRed){
        API.getInstance().sendRed(sendRed.getRequestNum(),sendRed.getUserId(),sendRed.getGroupId(),sendRed.getAmount(),sendRed.getType(),this);
    }

    public void analyzeRes(RspSendRed rspRed) {
        String result=rspRed.getResult();
        if (result.equals("success")){
            mView.reFreshCustemRed(rspRed.getData());
        }else {
            Toaster.toastShort(rspRed.getErrorMsg());
        }
    }
}
