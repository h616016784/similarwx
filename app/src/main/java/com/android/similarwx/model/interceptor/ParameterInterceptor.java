package com.android.similarwx.model.interceptor;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by puyafeng on 2017/9/13.
 * 参数拦截器
 * //添加UserId，token，imei，手机本地时间戳
 *
 */

public class ParameterInterceptor implements Interceptor {
    Map<String,Object> map;
    public ParameterInterceptor(Map<String,Object> map){
        this.map = map;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();
        if(map == null){
            return chain.proceed(original);
        }
        Iterator<Map.Entry<String,Object>> it = map.entrySet().iterator();
        HttpUrl.Builder builder = originalHttpUrl.newBuilder();
        while(it.hasNext()){
            Map.Entry<String, Object> entry = it.next();
            builder.addQueryParameter(entry.getKey(),entry.getValue()+"");
        }
        HttpUrl url = builder.build();
        Request.Builder requestBuilder = original.newBuilder()
                .url(url);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
