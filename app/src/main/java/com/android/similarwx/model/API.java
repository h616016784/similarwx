package com.android.similarwx.model;


import com.android.similarwx.model.convertor.JsonConverterFactory;
import com.android.similarwx.model.interceptor.LogInterceptor;
import com.android.similarwx.model.interceptor.NetCacheInterceptor;
import com.android.similarwx.model.interceptor.ParameterInterceptor;

import java.io.File;

import javax.inject.Inject;

import cn.fframe.f;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by puyafeng on 2017/9/12.
 */
public class API implements APIConstants {
    private static API sInstance;

    @Inject
    APIDesc apiDesc;

    private API() {
        OkHttpClient client = new OkHttpClient.Builder().
                addNetworkInterceptor(new NetCacheInterceptor()).
                addInterceptor(new ParameterInterceptor(new RequestParams())).
                addInterceptor(new LogInterceptor()).
                cache(new Cache(new File(f.getContext().getCacheDir(), f.getContext().getPackageName()), 10 * 1024 * 1024)).
                build();
        Retrofit retrofit = new Retrofit.Builder().client(client).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(JsonConverterFactory.create()).baseUrl(DOMAIN).build();
        apiDesc = retrofit.create(APIDesc.class);
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

    public APIDesc getApiDesc() {
        return apiDesc;
    }

}
