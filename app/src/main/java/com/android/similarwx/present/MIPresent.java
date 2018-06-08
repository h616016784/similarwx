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
        API.getInstance().sendRed(sendRed.getRequestNum(),sendRed.getMyUserId(),sendRed.getMyGroupId(),sendRed.getAmount(),sendRed.getType(),this);
    }

    public void analyzeRes(RspSendRed rspRed) {
        if (rspRed!=null){
            String result=rspRed.getResult();
            if (result.equals("success")){
                SendRed sendRed=rspRed.getData();
                if (sendRed!=null){
                    String code=sendRed.getRetCode();
                    if (code.equals("0000")){
                        mView.reFreshCustemRed(sendRed);
                    }else {
                        Toaster.toastShort(sendRed.getRetMsg());
                    }
                }else {
                    Toaster.toastShort("解析异常");
                }

            }else {
                Toaster.toastShort(rspRed.getErrorMsg());
            }
        }
    }
}
