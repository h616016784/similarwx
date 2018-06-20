package com.android.similarwx.model.interceptor;

import android.util.Log;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.utils.DigestUtil;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;

/**
 * Created by puyafeng on 2017/9/13.
 * 参数拦截器
 *
 */

public class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //获得请求信息，此处如有需要可以添加headers信息
        Request request = chain.request();
        //打印请求信息
        Log.e("request","url:" + request.url());
        Log.e("request","method:" + request.method());
        RequestBody requestBody=request.body();
        BufferedSink bufferedSink=new Buffer();
        requestBody.writeTo(bufferedSink);
        Log.e("request","request-body:" +bufferedSink.buffer().readString(Charset.forName("UTF-8")));
        //为请求添加header信息
        String useKey=SharePreferenceUtil.getString(AppContext.getContext(), AppConstants.USER_KEY,"userKey");
        String nonce=UUID.randomUUID().toString();
        String curTime=System.currentTimeMillis()+"";
        String checkSum= DigestUtil.sha1(useKey+nonce+curTime);
        Request requestNew=request.newBuilder().addHeader("useKey",useKey )
                .addHeader("nonce",nonce )
                .addHeader("curTime", curTime)
                .addHeader("checkSum", checkSum)
                .build();
        Log.e("request","headers:" + requestNew.headers());

        //记录请求耗时
        long startNs = System.nanoTime();
        Response response;
        try {
            //发送请求，获得相应，
            response = chain.proceed(requestNew);
        } catch (Exception e) {
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        //打印请求耗时
        Log.e("response","耗时:" + tookMs + "ms");
        //使用response获得headers(),可以更新本地Cookie。
        Headers headers = response.headers();
        Log.e("response",headers.toString());

        //获得返回的body，注意此处不要使用responseBody.string()获取返回数据，原因在于这个方法会消耗返回结果的数据(buffer)
        ResponseBody responseBody = response.body();

        //为了不消耗buffer，我们这里使用source先获得buffer对象，然后clone()后使用
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        //获得返回的数据
        Buffer buffer = source.buffer();
        //使用前clone()下，避免直接消耗
        Log.e("response","response:" + buffer.clone().readString(Charset.forName("UTF-8")));
        return response;
    }
}
