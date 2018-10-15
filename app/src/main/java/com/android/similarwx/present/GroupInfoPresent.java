package com.android.similarwx.present;

import android.support.v7.app.AppCompatActivity;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.GroupUser;
import com.android.similarwx.beans.response.RspDeleteGroup;
import com.android.similarwx.beans.response.RspGroupUser;
import com.android.similarwx.inteface.GroupInfoViewInterface;
import com.android.similarwx.model.API;

import java.util.List;

/**
 * Created by hanhuailong on 2018/6/4.
 */

public class GroupInfoPresent extends BasePresent {
    private GroupInfoViewInterface mView;
    AppCompatActivity activity;
    public GroupInfoPresent(GroupInfoViewInterface view,AppCompatActivity activity){
        this.mView=view;
        this.activity=activity;
    }

    public void getGroupUserList(String groupId){
        API.getInstance().GroupInfoPresent(activity,groupId,this);
    }

    public void doDeleteGroup(String groupId){
        API.getInstance().doDeleteGroup(activity,groupId,this);
    }

    public void getGroupUser(String groupId,String userId){
        API.getInstance().getGroupUser(activity,groupId,userId,this);
    }
    public void analyzeRes(RspGroupUser rspGroup) {
        String result=rspGroup.getResult();
        if (result.equals("success")){
            GroupUser list=rspGroup.getData();
            mView.refreshUserlist(list);
        }else {
            Toaster.toastShort(rspGroup.getErrorMsg());
        }
    }
    public void analyzeDeleteGroup(RspDeleteGroup rspGroup) {
        String result=rspGroup.getResult();
        if (result.equals("success")){
            mView.refreshDeleteGroup();
        }else {
            Toaster.toastShort(rspGroup.getErrorMsg());
        }
    }
}
