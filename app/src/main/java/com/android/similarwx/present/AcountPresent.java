package com.android.similarwx.present;

import android.support.v7.app.AppCompatActivity;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.response.RspAccountDetail;
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
    private AppCompatActivity activity;
    public AcountPresent(AcountViewInterface view,AppCompatActivity activity){
        this.mView=view;
        this.activity=activity;
    }

    public void getAcountList(String userId,String type,String rebateToUserId,String startDate,String endDate,String page,String rows){
        API.getInstance().getBill(activity,userId,type,rebateToUserId,startDate,endDate,page,rows,this);
    }
    public void getSendRedList(String userId,String rebateToUserId,String type,String startDate,String endDate,String page,String rows){
        API.getInstance().getSendRedBill(activity,userId,rebateToUserId,type,startDate,endDate,page,rows,this);
    }
    public void getAccountDetail(String accountDetailId){
        API.getInstance().getAccountDetail(activity,accountDetailId,this);
    }
    public void analyzeRes(RspBill rspBill) {
        if (rspBill!=null){
            String result=rspBill.getResult();
            if (result.equals("success")){
                if (rspBill.getErrorCode().equals("0000"))
                    mView.refreshBill(rspBill.getData());
                else
                    Toaster.toastShort(rspBill.getErrorMsg());
            }else {
                Toaster.toastShort(rspBill.getErrorMsg());
            }
        }
    }

    public void analyzeRes(RspAccountDetail rspAccountDetail) {
        if (rspAccountDetail!=null){
            String result=rspAccountDetail.getResult();
            if (result.equals("success")){
                if (rspAccountDetail.getErrorCode().equals("0000"))
                    mView.refreshAccountDetaiol(rspAccountDetail.getData());
                else
                    Toaster.toastShort(rspAccountDetail.getErrorMsg());
            }else {
                Toaster.toastShort(rspAccountDetail.getErrorMsg());
            }
        }
    }
}
