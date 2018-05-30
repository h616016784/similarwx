package com.android.similarwx.present;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.response.RspRed;
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
    public void sendRed(String groupId){
        API.getInstance().sendRed(groupId,this);
    }

    public void analyzeRes(RspRed rspRed) {
        String result=rspRed.getResult();
        if (result.equals("success")){
            mView.senCustemRed(rspRed.getData());
        }else {
            Toaster.toastShort(rspRed.getErrorMsg());
        }
    }
}
