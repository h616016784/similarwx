package com.android.similarwx.model;

import android.text.TextUtils;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.AccToken;
import com.android.similarwx.beans.UserInfoWX;
import com.android.similarwx.beans.response.RspUser;
import com.android.similarwx.beans.response.RspWXCode;
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.interceptor.LogInterceptor;
import com.android.similarwx.model.interceptor.WXLogInterceptor;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hanhuailong on 2018/7/19.
 */

public class WXAPI implements APIConstants  {
    private static WXAPI sInstance;
    private static APIService apiService;
    private WXAPI() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new WXLogInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(DOMAIN_WX)
                .build();
        apiService = retrofit.create(APIService.class);
    }

    public static WXAPI getInstance() {
        if (sInstance == null) {
            synchronized (WXAPI.class) {
                if (sInstance == null) {
                    sInstance = new WXAPI();
                }
            }
        }
        return sInstance;
    }

    public void accessToken(String appid, String secret, String code, String grant_type, YCallBack<AccToken> callBack){
        Map<String,String> map=new HashMap<>();
        map.put("appid",appid);
        map.put("secret",secret);
        map.put("code",code);
        map.put("grant_type",grant_type);
        Call<AccToken> codeCall=apiService.accessToken(map);
        codeCall.enqueue(new Callback<AccToken>() {
            @Override
            public void onResponse(Call<AccToken> call, Response<AccToken> response) {
                try {
                   AccToken accToken= response.body();
                    if (callBack!=null)
                        callBack.callBack(accToken);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AccToken> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }
    public void userInfoWX(String access_token, String openid,YCallBack<UserInfoWX> callBack){
        Map<String,String> map=new HashMap<>();
        map.put("access_token",access_token);
        map.put("openid",openid);
        Call<UserInfoWX> codeCall=apiService.userInfoWX(map);
        codeCall.enqueue(new Callback<UserInfoWX>() {
            @Override
            public void onResponse(Call<UserInfoWX> call, Response<UserInfoWX> response) {
                try {
                    UserInfoWX userInfoWX= response.body();
                    if (callBack!=null)
                        callBack.callBack(userInfoWX);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<UserInfoWX> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }
}
