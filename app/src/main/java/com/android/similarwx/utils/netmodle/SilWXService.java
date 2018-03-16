package com.android.similarwx.utils.netmodle;

import com.android.similarwx.beans.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by hanhuailong on 2018/3/15.
 */

public interface SilWXService {
    @GET("users/login")
    Call<User> login(@Query("username") String username, @Query("password") String password);
    @POST("users/login")
    Call<User> login(@Body User user);
}
