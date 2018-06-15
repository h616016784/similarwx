package com.android.similarwx.present;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.response.RspBill;
import com.android.similarwx.beans.response.RspGroup;
import com.android.similarwx.beans.response.RspGroupApply;
import com.android.similarwx.inteface.AcountViewInterface;
import com.android.similarwx.inteface.MainGroupView;
import com.android.similarwx.model.API;
import com.android.similarwx.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/1.
 */

public class AcountPresent extends BasePresent {
    private AcountViewInterface mView;
    public AcountPresent(AcountViewInterface view){
        this.mView=view;
    }

    public void getAcountList(String userId,String type,String startDate,String endDate){
        API.getInstance().getBill(userId,type,startDate,endDate,this);
    }

    public void analyzeRes(RspBill rspBill) {
        if (rspBill!=null){
            String result=rspBill.getResult();
            if (result.equals("success")){
                
            }else {
                Toaster.toastShort(rspBill.getErrorMsg());
            }
        }
    }
}
