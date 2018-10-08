package com.android.similarwx.present;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.response.RspRedDetail;
import com.android.similarwx.inteface.RedDetailViewInterface;
import com.android.similarwx.model.API;

/**
 * Created by hanhuailong on 2018/6/19.
 */

public class RedDetailPresent {
    private RedDetailViewInterface mView;
    public RedDetailPresent(RedDetailViewInterface mView){
        this.mView=mView;
    }
    public void redDetailList(String redPacId,String groupId){
        API.getInstance().redDetailList(redPacId,groupId,this);
    }
    public void analyzeRes(RspRedDetail rspRedDetail) {
        if (rspRedDetail!=null){
            String result=rspRedDetail.getResult();
            if (result.equals("success")){
                mView.refreshRedDetail(rspRedDetail.getData());
            }else {
                Toaster.toastShort(rspRedDetail.getErrorMsg());
            }
        }
    }
}
