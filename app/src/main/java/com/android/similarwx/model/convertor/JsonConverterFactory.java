package com.android.similarwx.model.convertor;

import android.support.annotation.Nullable;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @desc: 由于后台传回来的json格式不规范,data部分有时候并不是object而是""，因此自定义转换器处理resp->泛型的过程来处理特殊情况
 * Created by puyafeng on 2018/4/2.
 */

public class JsonConverterFactory extends Converter.Factory {
    private final Gson gson;

    public static JsonConverterFactory create() {
        return create(new Gson());
    }

    public static JsonConverterFactory create(Gson gson) {
        if (gson == null) {
            throw new RuntimeException("gson对象不能为空");
        }
        return new JsonConverterFactory(new Gson());
    }

    public JsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(final Type type, Annotation[] annotations, Retrofit retrofit) {
        return new Converter<ResponseBody, Object>() {
            @Override
            public Object convert(ResponseBody value) throws IOException {
                Object t = null;
                String resp = null;
                try {
                    resp = new String(value.bytes());
                    if (type == String.class) {
                        t = resp;
                    } else {
                        try {
                            t = gson.fromJson(resp, type);
                        } catch (Exception e) {
                            JSONObject jo = new JSONObject(resp);
                            if (jo.has("data")) {//对后端无数据时返回不标准的json做处理（无数据，data部分应该返回空对象，但后端返回了空字符串）
                                Object data = jo.get("data");
                                if ("".equals(data + "")) {
//                                    jo.put("data", null);
                                    t = gson.fromJson(jo.toString(), type);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return t;
            }
        };
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }
}
