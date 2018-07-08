package com.android.similarwx.present;

import android.app.Activity;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.BaseBean;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.response.RspCanGrab;
import com.android.similarwx.beans.response.RspGrabRed;
import com.android.similarwx.beans.response.RspRed;
import com.android.similarwx.beans.response.RspSendRed;
import com.android.similarwx.inteface.MiViewInterface;
import com.android.similarwx.model.API;
import com.android.similarwx.utils.SharePreferenceUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/4/1.
 */

public class MIPresent extends BasePresent {
    private MiViewInterface mView;
    public MIPresent(MiViewInterface view){
        this.mView=view;
    }
    public void sendRed(SendRed sendRed){
        API.getInstance().sendRed(sendRed.getData().getRequestNum(),sendRed.getData().getUserId(),sendRed.getData().getMyUserId(),sendRed.getData().getMyGroupId(),sendRed.getData().getAmount(),sendRed.getData().getType(),sendRed.getData().getCount(),sendRed.getData().getThunder(),this);
    }

    public void canGrab(String redId, Activity activity){
        String userId= SharePreferenceUtil.getString(AppContext.getContext(), AppConstants.USER_ID,"无");
        API.getInstance().canGrab(userId,redId,this,activity);
    }
    public void grabRed(String redId, Activity activity){
        String userId= SharePreferenceUtil.getString(AppContext.getContext(), AppConstants.USER_ID,"无");
        API.getInstance().grabRed(userId,redId,this,activity);
    }
    public void analyzeRes(RspSendRed rspRed,String accid) {
        if (rspRed!=null){
            String result=rspRed.getResult();
            if (result.equals("success")){
                SendRed.SendRedBean sendRed=rspRed.getData();
                if (sendRed!=null){
                    String code=sendRed.getRetCode();
                    if (code.equals("0000")){
                        sendRed.setMyUserId(accid);
                        mView.reFreshCustemRed(sendRed);
                    }else {
                        Toaster.toastShort(sendRed.getRetMsg());
                    }
                }else {
                    Toaster.toastShort("解析异常");
                }
            }else {
                Toaster.toastShort(rspRed.getErrorMsg());
            }
        }
    }
    public void analyzeRes(RspGrabRed rspRed) {
        mView.grabRed(rspRed);
    }

    public void analyzeCanRed(RspCanGrab grabRed) {
        if (grabRed != null) {
            String result = grabRed.getResult();
            if (result.equals("success")) {
                BaseBean bena = grabRed.getData();
                mView.canGrab(bena);
            }else {
                Toaster.toastShort(grabRed.getErrorMsg());
            }
        }
    }
}
