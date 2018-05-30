package com.android.similarwx.model;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspGroup;
import com.android.similarwx.beans.response.RspNotice;
import com.android.similarwx.beans.response.RspRed;
import com.android.similarwx.beans.response.RspUser;
import com.android.similarwx.model.interceptor.LogInterceptor;
import com.android.similarwx.present.GroupPresent;
import com.android.similarwx.present.LoginPresent;
import com.android.similarwx.present.MIPresent;
import com.android.similarwx.present.NoticePresent;
import com.android.similarwx.present.RegisterPresent;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by puyafeng on 2017/9/12.
 */
public class API implements APIConstants {
    private static API sInstance;
    private static APIService apiService;
    private API() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LogInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(DOMAIN)
                .build();
        apiService = retrofit.create(APIService.class);
    }

    public static API getInstance() {
        if (sInstance == null) {
            synchronized (API.class) {
                if (sInstance == null) {
                    sInstance = new API();
                }
            }
        }
        return sInstance;
    }

    /**
     * 注册接口
     * @param account
     * @param weixinAccount
     * @param email
     * @param name
     * @param password
     * @param nick
     * @param present
     */
    public void register(String account, String weixinAccount, String email, String name, String password, String nick, final RegisterPresent present){
        Map<String,String> map=new HashMap<>();
        map.put("accId",account);
        map.put("passwdStr",password);
        map.put("email",email);
        map.put("mobile",name);
        map.put("wechatAccount",weixinAccount);
        map.put("name",nick);
        Call<RspUser> user=apiService.registe(map);
        user.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                RspUser rspUser=response.body();
                present.analyzeRes(rspUser);
            }
            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {

            }
        });
    }
    public void login(String name, String password, String weixin, String mobile, final LoginPresent present){
        Map<String,String> map=new HashMap<>();
        map.put("accId",name);
        map.put("mobile",mobile);
        map.put("wechatAccount",weixin);
        map.put("passwdStr",password);
        Call<RspUser> user=apiService.login(map);
        user.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                try {
                    RspUser rspUser=response.body();
                    present.analyzeRes(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }

    public void reqGroupList(String userId , final GroupPresent present){
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId);
        Call<RspGroup> rspGroupCall=apiService.reqGroupList(map);
        rspGroupCall.enqueue(new Callback<RspGroup>() {
            @Override
            public void onResponse(Call<RspGroup> call, Response<RspGroup> response) {
                try {
                    RspGroup rspGroup=response.body();
                    present.analyzeRes(rspGroup);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspGroup> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }

    public void getNotices(final NoticePresent present) {
        Map<String,String> map=new HashMap<>();
        Call<RspNotice> rspNoticeCall=apiService.getNotices(map);
        rspNoticeCall.enqueue(new Callback<RspNotice>() {
            @Override
            public void onResponse(Call<RspNotice> call, Response<RspNotice> response) {
                try {
                    RspNotice rspNotice=response.body();
                    present.analyzeRes(rspNotice);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspNotice> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }

    public void sendRed(String groupId, final MIPresent present) {
        Map<String,String> map=new HashMap<>();
        map.put("groupId",groupId);
        Call<RspRed> rspRedCall=apiService.sendRed(map);
        rspRedCall.enqueue(new Callback<RspRed>() {
            @Override
            public void onResponse(Call<RspRed> call, Response<RspRed> response) {
                RspRed rspRed=response.body();
                present.analyzeRes(rspRed);
            }

            @Override
            public void onFailure(Call<RspRed> call, Throwable t) {

            }
        });
    }
}
