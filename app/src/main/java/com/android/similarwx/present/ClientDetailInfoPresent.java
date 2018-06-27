package com.android.similarwx.present;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspUser;
import com.android.similarwx.inteface.ClientDetailInfoViewInterface;
import com.android.similarwx.model.API;

/**
 * Created by hanhuailong on 2018/6/27.
 */

public class ClientDetailInfoPresent {
    ClientDetailInfoViewInterface mView;
    public ClientDetailInfoPresent(ClientDetailInfoViewInterface mView){
        this.mView=mView;
    }
    public void getUserInfoByParams(String userid,String accId){
        API.getInstance().getUserInfoByParams(userid,accId,this);
    }
    public void analyzeRes(RspUser rspUser) {
        String result=rspUser.getResult();
        if (result.equals("success")){
            User user=rspUser.getData();
            if (user!=null){
                mView.refreshUserInfo(user);
            }else
                Toaster.toastShort("数据解析异常");
        }else {
            Toaster.toastShort(rspUser.getErrorMsg());
        }
    }


}
