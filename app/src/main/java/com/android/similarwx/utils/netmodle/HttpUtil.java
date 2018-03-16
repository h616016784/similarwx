package com.android.similarwx.utils.netmodle;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hanhuailong on 2018/3/15.
 */

public class HttpUtil {
    private static  HttpUtil mHttpUtil;
    private SilWXService silWXService;
    private HttpUtil(){
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(new LogIntercept())
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ServiceConstants.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        silWXService=retrofit.create(SilWXService.class);
    }
    public static HttpUtil getInstance(){
        if (mHttpUtil == null) {
            synchronized (HttpUtil.class) {
                if (mHttpUtil == null) {
                    mHttpUtil = new HttpUtil();
                }
            }
        }
        return mHttpUtil;
    }
    public SilWXService getServiceHandler(){
        return silWXService;
    }

}
