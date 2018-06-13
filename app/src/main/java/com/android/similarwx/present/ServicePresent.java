package com.android.similarwx.present;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.response.RspSendRed;
import com.android.similarwx.beans.response.RspService;
import com.android.similarwx.inteface.ServiceViewInterface;
import com.android.similarwx.model.API;

/**
 * Created by hanhuailong on 2018/6/13.
 */

public class ServicePresent extends BasePresent {
    private ServiceViewInterface mView;
    public ServicePresent(ServiceViewInterface view){
        this.mView=view;
    }
    public void getServicesList(){
        API.getInstance().getServicesList(3+"",this);
    }
    public void analyzeRes(RspService rspService) {
        if (rspService!=null){
            String result=rspService.getResult();
            if (result.equals("success")){
                RspService.DataBean sendRed=rspService.getData();
                if (sendRed!=null){
                    mView.serviceRefresh(sendRed.getResUsersDetailList());
//                    String code=sendRed.getRetCode();
//                    if (code.equals("0000")){
//
//                    }else {
//                        Toaster.toastShort(sendRed.getRetMsg());
//                    }
                }else {
                    Toaster.toastShort("解析异常");
                }
            }else {
                Toaster.toastShort(rspService.getErrorMsg());
            }
        }
    }
}
