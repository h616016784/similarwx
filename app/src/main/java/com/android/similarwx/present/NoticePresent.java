package com.android.similarwx.present;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.Notice;
import com.android.similarwx.beans.response.RspAddGroupUser;
import com.android.similarwx.beans.response.RspNotice;
import com.android.similarwx.inteface.NoticeViewInterface;
import com.android.similarwx.model.API;

import java.util.List;

/**
 * Created by hanhuailong on 2018/5/28.
 */

public class NoticePresent extends BasePresent {
    private NoticeViewInterface mView;

    public NoticePresent(NoticeViewInterface mView) {
        this.mView = mView;
    }

    public void doAddGroupUser(String groupId,String userId){
        API.getInstance().doAddGroupUser(groupId,userId,this);
    }
    public void analyzeRes(RspAddGroupUser rspNotice) {
        String result=rspNotice.getResult();
        if (result.equals("success")){

        }else {
            Toaster.toastShort(rspNotice.getErrorMsg());
        }
    }
}
