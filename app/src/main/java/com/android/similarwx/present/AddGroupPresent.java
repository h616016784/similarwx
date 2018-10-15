package com.android.similarwx.present;

import android.support.v7.app.AppCompatActivity;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.request.ReqGroup;
import com.android.similarwx.beans.response.RspGroupInfo;
import com.android.similarwx.beans.response.RspGroupSave;
import com.android.similarwx.inteface.AddGroupViewInterface;
import com.android.similarwx.model.API;

import java.util.List;

/**
 * Created by hanhuailong on 2018/6/20.
 */

public class AddGroupPresent {
    private AddGroupViewInterface mView;
    private AppCompatActivity activity;
    public AddGroupPresent(AddGroupViewInterface mView,AppCompatActivity activity){
        this.mView=mView;
        this.activity=activity;
    }
    public void addGroup(RspGroupInfo.GroupInfo reqGroup){
        API.getInstance().groupSave(activity,reqGroup,this,"",0);
    }
    public void updateGroup(RspGroupInfo.GroupInfo reqGroup,String updateUserId){
        API.getInstance().groupSave(activity,reqGroup,this,updateUserId,1);
    }
    public void analyzeAddGroup(RspGroupSave rspGroupSave) {
        if (rspGroupSave!=null){
            String result=rspGroupSave.getResult();
            if (result.equals("success")){
                if (rspGroupSave.getErrorCode().equals("0000"))
                    mView.refreshAddGroup();
                else
                    Toaster.toastShort(rspGroupSave.getErrorMsg());
            }else {
                Toaster.toastShort(rspGroupSave.getErrorMsg());
            }
        }
    }
}
