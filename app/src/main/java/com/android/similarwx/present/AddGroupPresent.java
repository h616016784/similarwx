package com.android.similarwx.present;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.request.ReqGroup;
import com.android.similarwx.beans.response.RspGroupSave;
import com.android.similarwx.inteface.AddGroupViewInterface;
import com.android.similarwx.model.API;

import java.util.List;

/**
 * Created by hanhuailong on 2018/6/20.
 */

public class AddGroupPresent {
    private AddGroupViewInterface mView;
    public AddGroupPresent(AddGroupViewInterface mView){
        this.mView=mView;
    }
    public void addGroup(ReqGroup reqGroup){
        API.getInstance().groupSave(reqGroup,this);
    }

    public void analyzeAddGroup(RspGroupSave rspGroupSave) {
        if (rspGroupSave!=null){
            String result=rspGroupSave.getResult();
            if (result.equals("success")){
                mView.refreshAddGroup();
            }else {
                Toaster.toastShort(rspGroupSave.getErrorMsg());
            }
        }
    }
}