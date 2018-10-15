package com.android.similarwx.present;

import android.support.v7.app.AppCompatActivity;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.GroupUser;
import com.android.similarwx.beans.response.RspGroupUser;
import com.android.similarwx.beans.response.RspUser;
import com.android.similarwx.inteface.SearchViewInterface;
import com.android.similarwx.model.API;

/**
 * Created by hanhuailong on 2018/8/1.
 */

public class SearchPresent extends BasePresent {
    private SearchViewInterface view;
    AppCompatActivity activity;
    public SearchPresent(SearchViewInterface view,AppCompatActivity activity){
        this.view=view;
        this.activity=activity;
    }
    public void searchUser(String groupId,String username){
        API.getInstance().getSearchUser(activity,groupId,username,this);
    }
    public void searchUser(String params){
        API.getInstance().getSearchUser(activity,params,this);
    }
    public void searchTeam(){

    }
    public void analyzeRes(RspUser rspGroup) {
        String result=rspGroup.getResult();
        if (result.equals("success")){
            String code=rspGroup.getErrorCode();
            if (code.equals("0000")){
                view.refreshSearchUser(rspGroup.getData());
            }else
                Toaster.toastShort(rspGroup.getErrorMsg());
        }else
            Toaster.toastShort(rspGroup.getErrorMsg());

    }
    public void analyzeRes(RspGroupUser rspGroup) {
        String result=rspGroup.getResult();
        if (result.equals("success")){
            String code=rspGroup.getErrorCode();
            if (code.equals("0000")){
                view.refreshSearchUser();
            }else
                Toaster.toastShort(rspGroup.getErrorMsg());
        }else
            Toaster.toastShort(rspGroup.getErrorMsg());

    }
}
