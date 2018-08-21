package com.android.similarwx.present;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspGroup;
import com.android.similarwx.beans.response.RspGroupApply;
import com.android.similarwx.beans.response.RspUser;
import com.android.similarwx.inteface.MainGroupView;
import com.android.similarwx.model.API;
import com.android.similarwx.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/1.
 */

public class GroupPresent extends BasePresent {
    private MainGroupView mView;
    public GroupPresent(MainGroupView view){
        this.mView=view;
    }
    public void getGroupList(){
        String accid=SharePreferenceUtil.getString(AppContext.getContext(), AppConstants.USER_ACCID,"无");
        API.getInstance().reqGroupList(accid,this);
    }

    public void analyzeRes(RspGroup rspGroup) {
        if (rspGroup!=null){
            String result=rspGroup.getResult();
            if (result.equals("success")){
                List<GroupMessageBean.ListBean> list=rspGroup.getData().getList();
                mView.groupRefresh(list);
            }else {
                Toaster.toastShort(rspGroup.getErrorMsg());
            }
        }
    }

    public void doGroupAppley(String groupId) {
        String accid=SharePreferenceUtil.getString(AppContext.getContext(), AppConstants.USER_ACCID,"无");
        API.getInstance().doGroupAppley(groupId,accid,this);
    }

    public void analyzeApplyRes(RspGroupApply rspGroupApply) {
        if (rspGroupApply!=null){
            String result=rspGroupApply.getResult();
            if (result.equals("success")){
                mView.groupApply("success");
            }else {
                Toaster.toastShort(rspGroupApply.getErrorMsg());
            }
        }
    }
}
