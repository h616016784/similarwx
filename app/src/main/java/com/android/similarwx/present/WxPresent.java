package com.android.similarwx.present;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspUser;
import com.android.similarwx.inteface.WxViewInterface;
import com.android.similarwx.model.API;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hanhuailong on 2018/7/30.
 */

public class WxPresent extends BasePresent {
    private WxViewInterface view;
    private String unionId;
    AppCompatActivity activity;
    public WxPresent(WxViewInterface view,AppCompatActivity activity){
        this.view=view;
        this.activity=activity;
    }

    public void WxLogin(String unionId){
        this.unionId=unionId;
        API.getInstance().WxLogin(activity,unionId,this);
    }
    public void analyzeRes(RspUser rspUser) {
        String result=rspUser.getResult();
        if (result.equals("success")){
            if (rspUser.getErrorCode().equals("0000")){
                User user=rspUser.getData();
                if (user!=null){
                    view.refreshWxLogin(user);
                }else
                    Toaster.toastShort("数据解析异常");
            }else if (rspUser.getErrorCode().equals("1111")){
                User user=rspUser.getData();
                if (user!=null){
                    view.refreshWxUpdate(user);
                }else
                    Toaster.toastShort("数据解析异常");
            }else{
                Toaster.toastShort(rspUser.getErrorMsg());
//                doRigister(unionId);
            }
        }else{
            Toaster.toastShort(rspUser.getErrorMsg());
        }
    }

    private void doRigister(String accid) {
        Map<String,String> map=new HashMap<>();
        if (!TextUtils.isEmpty(accid))
            map.put("accId",accid);
        Call<RspUser> user=API.getInstance().apiService.registe(map);
        user.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                RspUser rspUser=response.body();
                String result=rspUser.getResult();
                if (result.equals("success")){
                    if (rspUser.getErrorCode().equals("1111")){
                        User user=rspUser.getData();
                        if (user!=null){
                            view.refreshWxUpdate(user);
                        }else
                            Toaster.toastShort("数据解析异常");
                    }
                }else {
                    Toaster.toastShort(rspUser.getErrorMsg());
                }

            }
            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {

            }
        });
    }


}
