package com.android.similarwx.present;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspDeleteGroupUser;
import com.android.similarwx.beans.response.RspUpdateGroupUser;
import com.android.similarwx.beans.response.RspUser;
import com.android.similarwx.inteface.ClientDetailInfoViewInterface;
import com.android.similarwx.model.API;

/**
 * Created by hanhuailong on 2018/6/27.
 */

public class ClientDetailInfoPresent {
    ClientDetailInfoViewInterface mView;
    public ClientDetailInfoPresent(ClientDetailInfoViewInterface mView){
        this.mView=mView;
    }
    public void getUserInfoByParams(String userid,String accId){
        API.getInstance().getUserInfoByParams(userid,accId,this);
    }

    public void doUpdateGroupUser(String groupId,String userId,String groupUserRule){
        API.getInstance().doUpdateGroupUser(groupId,userId,groupUserRule,this);
    }

    public void doDeleteGroupUser(String groupId,String userId){
        API.getInstance().doDeleteGroupUser(groupId,userId,this);
    }



    public void analyzeRes(RspUser rspUser) {
        String result=rspUser.getResult();
        if (result.equals("success")){
            User user=rspUser.getData();
            if (user!=null){
                mView.refreshUserInfo(user);
            }else
                Toaster.toastShort("数据解析异常");
        }else {
            Toaster.toastShort(rspUser.getErrorMsg());
        }
    }
    public void analyzeUpdateGroupUser(RspUpdateGroupUser rspUser) {
        String result=rspUser.getResult();
        if (result.equals("success")){
            mView.refreshUpdateUser();
        }else {
            Toaster.toastShort(rspUser.getErrorMsg());
        }
    }

    public void analyzeDeleteGroupUser(RspDeleteGroupUser rspDeleteGroupUser) {
        String result=rspDeleteGroupUser.getResult();
        if (result.equals("success")){
            mView.refreshDeleteUser();
        }else {
            Toaster.toastShort(rspDeleteGroupUser.getErrorMsg());
        }
    }
}
