package com.android.similarwx.model;

import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspUser;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2018/4/27.
 */

public interface APIService {

    @POST("resUsers/register")
    Call<RspUser> login(@QueryMap Map<String,String> map);
}
