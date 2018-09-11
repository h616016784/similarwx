package com.android.similarwx.present;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.response.RspGroupInfo;
import com.android.similarwx.inteface.SendRedViewInterface;
import com.android.similarwx.model.API;

/**
 * Created by hanhuailong on 2018/7/12.
 */

public class SendRedPresent {
    private SendRedViewInterface view;
    public SendRedPresent(SendRedViewInterface view){
        this.view=view;
    }
    public void getGroupByIdOrGroupId(String id,String groupId){
        API.getInstance().getGroupByGroupId(id,groupId,this);
    }
    public void analyzeRes(RspGroupInfo rspGroupInfo) {
        if (rspGroupInfo!=null){
            String result=rspGroupInfo.getResult();
            if (result.equals("success")){
                if (rspGroupInfo.getErrorCode().equals("0000")){
                    RspGroupInfo.GroupInfo groupInfo=rspGroupInfo.getData();
                    view.reFreshSendRed(groupInfo);
                }else
                    Toaster.toastShort(rspGroupInfo.getErrorMsg());
            }else {
                Toaster.toastShort(rspGroupInfo.getErrorMsg());
            }
        }
    }
}
