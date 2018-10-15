package com.android.similarwx.present;

import android.support.v7.app.AppCompatActivity;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.Notice;
import com.android.similarwx.beans.response.RspAddGroupUser;
import com.android.similarwx.beans.response.RspNotice;
import com.android.similarwx.inteface.NoticeViewInterface;
import com.android.similarwx.model.API;
import com.netease.nimlib.sdk.msg.model.SystemMessage;

import java.util.List;

/**
 * Created by hanhuailong on 2018/5/28.
 */

public class NoticePresent extends BasePresent {
    private NoticeViewInterface mView;
    private SystemMessage mMessage;
    AppCompatActivity activity;
    private String groupId;
    private int tag=0;
    public NoticePresent(NoticeViewInterface mView,AppCompatActivity activity) {
        this.mView = mView;
        this.activity=activity;
    }

    public void doAddGroupUser(SystemMessage message){
        tag=0;
        mMessage=message;
        groupId=message.getTargetId();
        API.getInstance().doAddGroupUser(activity,message.getTargetId(),message.getFromAccount(),this);
    }
    public void doAddGroupUser(String groupId,String accid){
        tag=1;
        this.groupId=groupId;
        API.getInstance().doAddGroupUser(activity,groupId,accid,this);
    }
    public void analyzeRes(RspAddGroupUser rspNotice) {
        String result=rspNotice.getResult();
        if (result.equals("success")){
            if (tag==0)
                mView.aggreeView(mMessage);
            else if (tag==1){
                mView.aggreeView(rspNotice.getErrorCode(),groupId);
            }
        }else {
            Toaster.toastShort(rspNotice.getErrorMsg());
        }
    }
}
