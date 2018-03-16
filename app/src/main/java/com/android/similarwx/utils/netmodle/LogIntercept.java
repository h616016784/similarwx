package com.android.similarwx.utils.netmodle;

import com.android.outbaselibrary.utils.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;

/**
 * Created by hanhuailong on 2018/3/16.
 */

public class LogIntercept implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Buffer buffer=new Buffer();
        buffer.request(Long.MAX_VALUE);

        Request request=chain.request();
        //过滤修改请求参数
        Request requestNew = request.newBuilder()
                .url(request.url())
                .addHeader("User-Agent", "OkHttp Example")
                .build();
        Long t1=System.nanoTime();
        RequestBody body=requestNew.body();
        body.writeTo(buffer);
        LogUtil.d("Url:"+requestNew.url()+";Method:"+requestNew.method()+";header:"+requestNew.headers()+";body:"+buffer.clone().readString(Charset.forName("UTF-8")));
        buffer.clear();
        //
        Response response = chain.proceed(requestNew);

        long t2 = System.nanoTime();
        ResponseBody responseBody=response.body();
        BufferedSource bufferedSource=responseBody.source();
        bufferedSource.request(Long.MAX_VALUE);
        buffer=bufferedSource.buffer();
        LogUtil.d(String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, buffer.clone().readString(Charset.forName("UTF-8"))));
        buffer.clear();
        buffer.close();
        return response;
    }
}
