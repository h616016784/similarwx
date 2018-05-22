package com.android.similarwx.model.interceptor;

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
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
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
        Logger.e("url:" + request.url());
        Logger.e("method:" + request.method());
        Logger.e("request-body:" + request.body());
        //为请求添加header信息
        String useKey=SharePreferenceUtil.getString(AppContext.getContext(), AppConstants.USER_KEY,"userKey");
        String nonce=UUID.randomUUID().toString();
        String curTime=System.currentTimeMillis()+"";
        String checkSum= DigestUtil.sha1(useKey+nonce+curTime);
        request=request.newBuilder().addHeader("useKey",useKey )
                .addHeader("nonce",nonce )
                .addHeader("curTime", curTime)
                .addHeader("checkSum", checkSum)
                .build();
        Logger.e("headers:" + request.headers());

        //记录请求耗时
        long startNs = System.nanoTime();
        Response response;
        try {
            //发送请求，获得相应，
            response = chain.proceed(request);
        } catch (Exception e) {
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        //打印请求耗时
        Logger.e("耗时:" + tookMs + "ms");
        //使用response获得headers(),可以更新本地Cookie。
        Headers headers = response.headers();
        Logger.e(headers.toString());

        //获得返回的body，注意此处不要使用responseBody.string()获取返回数据，原因在于这个方法会消耗返回结果的数据(buffer)
        ResponseBody responseBody = response.body();

        //为了不消耗buffer，我们这里使用source先获得buffer对象，然后clone()后使用
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        //获得返回的数据
        Buffer buffer = source.buffer();
        //使用前clone()下，避免直接消耗
        Logger.e("response:" + buffer.clone().readString(Charset.forName("UTF-8")));

        return response;
    }
}
