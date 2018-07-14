package com.android.similarwx.present;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.response.RspGroup;
import com.android.similarwx.beans.response.RspSubUsers;
import com.android.similarwx.inteface.SubUsersViewInterface;
import com.android.similarwx.model.API;
import com.android.similarwx.utils.SharePreferenceUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/7/14.
 */

public class SubUsersPresent {
    SubUsersViewInterface view;
    public SubUsersPresent(SubUsersViewInterface view){
        this.view=view;
    }
    public void getSubUsers(String sordType, String subUserIdenti, String page,String pageSize){
        String userId= SharePreferenceUtil.getString(AppContext.getContext(), AppConstants.USER_ID,"无");
        API.getInstance().subUserList(userId,sordType,subUserIdenti,page,pageSize,this);
    }
    public void analyzeRes(RspSubUsers rspGroup) {
        if (rspGroup!=null){
            String result=rspGroup.getResult();
            if (result.equals("success")){
                 view.refreshSubUsers(rspGroup.getData().getList());
            }else {
                Toaster.toastShort(rspGroup.getErrorMsg());
            }
        }
    }

}
