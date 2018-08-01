package com.android.similarwx.present;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.GroupUser;
import com.android.similarwx.beans.response.RspGroupUser;
import com.android.similarwx.inteface.SearchViewInterface;
import com.android.similarwx.model.API;

/**
 * Created by hanhuailong on 2018/8/1.
 */

public class SearchPresent extends BasePresent {
    private SearchViewInterface view;
    public SearchPresent(SearchViewInterface view){
        this.view=view;
    }
    public void searchUser(String username){
        API.getInstance().getSearchUser("",username,this);
    }
    public void searchTeam(){

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
