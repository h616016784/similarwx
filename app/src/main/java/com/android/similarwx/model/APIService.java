package com.android.similarwx.model;

import com.android.similarwx.beans.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2018/4/27.
 */

public interface APIService {
    @GET("users/{user}/repos")
    Call<User> login(@Path("user") String user);
}
