package com.android.similarwx.present;

import android.support.v7.app.AppCompatActivity;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspDeleteGroupUser;
import com.android.similarwx.beans.response.RspUpdateGroupUser;
import com.android.similarwx.beans.response.RspUpdateUserStatus;
import com.android.similarwx.beans.response.RspUser;
import com.android.similarwx.inteface.ClientDetailInfoViewInterface;
import com.android.similarwx.model.API;

/**
 * Created by hanhuailong on 2018/6/27.
 */

public class ClientDetailInfoPresent {
    ClientDetailInfoViewInterface mView;
    AppCompatActivity activity;
    private String userStatus;
    public ClientDetailInfoPresent(ClientDetailInfoViewInterface mView,AppCompatActivity activity){
        this.mView=mView;
        this.activity=activity;
    }
    public void getUserInfoByParams(String userid,String accId){
        API.getInstance().getUserInfoByParams(activity,userid,accId,this);
    }

    public void doUpdateGroupUser(String groupId,String userId,String groupUserRule){
        API.getInstance().doUpdateGroupUser(activity,groupId,userId,groupUserRule,this);
    }

    public void doDeleteGroupUser(String groupId,String userId){
        API.getInstance().doDeleteGroupUser(activity,groupId,userId,this);
    }

    public void doUpdateGroupUserStatus(String groupId,String userId,String userStatus){
        this.userStatus=userStatus;
        API.getInstance().doUpdateGroupUserStatus(activity,groupId,userId,userStatus,this);
    }

    public void analyzeRes(RspUser rspUser) {
        String result=rspUser.getResult();
        if (result.equals("success")){
            if (rspUser.getErrorCode().equals("0000")){
                User user=rspUser.getData();
                if (user!=null){
                    mView.refreshUserInfo(user);
                }else
                    Toaster.toastShort("数据解析异常");
            }else {
                Toaster.toastShort(rspUser.getErrorMsg());
            }
        }else {
            Toaster.toastShort(rspUser.getErrorMsg());
        }
    }
    public void analyzeUpdateGroupUser(RspUpdateGroupUser rspUser) {
        String result=rspUser.getResult();
        if (result.equals("success")){
            if (rspUser.getErrorCode().equals("0000")){
                mView.refreshUpdateUser();
            }else
                Toaster.toastShort(rspUser.getErrorMsg());
        }else {
            Toaster.toastShort(rspUser.getErrorMsg());
        }
    }

    public void analyzeDeleteGroupUser(RspDeleteGroupUser rspDeleteGroupUser) {
        String result=rspDeleteGroupUser.getResult();
        if (result.equals("success")){
            if (rspDeleteGroupUser.getErrorCode().equals("0000")){
                mView.refreshDeleteUser();
            }else
                Toaster.toastShort(rspDeleteGroupUser.getErrorMsg());
        }else {
            Toaster.toastShort(rspDeleteGroupUser.getErrorMsg());
        }
    }

    public void analyzeUpdateGroupUserStatus(RspUpdateUserStatus rspDeleteGroupUser) {
        String result=rspDeleteGroupUser.getResult();
        if (result.equals("success")){
            if (rspDeleteGroupUser.getErrorCode().equals("0000")){
                mView.refreshUpdateUserStatus(userStatus);
            }else
                Toaster.toastShort(rspDeleteGroupUser.getErrorMsg());
        }else {
            Toaster.toastShort(rspDeleteGroupUser.getErrorMsg());
        }
    }
}
