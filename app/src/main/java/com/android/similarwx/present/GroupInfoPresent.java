package com.android.similarwx.present;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.GroupUser;
import com.android.similarwx.beans.response.RspGroupUser;
import com.android.similarwx.inteface.GroupInfoViewInterface;
import com.android.similarwx.model.API;

import java.util.List;

/**
 * Created by hanhuailong on 2018/6/4.
 */

public class GroupInfoPresent extends BasePresent {
    private GroupInfoViewInterface mView;
    public GroupInfoPresent(GroupInfoViewInterface view){
        this.mView=view;
    }

    public void getGroupUserList(String groupId){
        API.getInstance().GroupInfoPresent(groupId,this);
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
}
