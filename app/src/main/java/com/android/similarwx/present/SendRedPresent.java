package com.android.similarwx.present;

import android.support.v7.app.AppCompatActivity;

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
    AppCompatActivity activity;
    public SendRedPresent(SendRedViewInterface view,AppCompatActivity activity){
        this.view=view;
        this.activity=activity;
    }
    public void getGroupByIdOrGroupId(String id,String groupId){
        API.getInstance().getGroupByGroupId(activity,id,groupId,this);
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
