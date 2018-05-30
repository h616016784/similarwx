package com.android.similarwx.misdk.model;

import com.alibaba.fastjson.JSONObject;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;

/**
 * Created by hanhuailong on 2018/5/2.
 */

public  class CustomAttachment<T> implements MsgAttachment{
    // 自定义消息附件的类型，根据该字段区分不同的自定义消息
    protected int type;
    protected T data;

    public CustomAttachment( T data) {
        this.data=data;
    }
    public CustomAttachment(int type, T data) {
        this.type = type;
        this.data=data;
    }
    // 解析附件内容。
    public String fromJson(T data) {
        String json = null;
        if (data != null) {
            try{
                json = JSONObject.toJSONString(data);
            }catch (Exception e){
                return null;
            }
        }
        return json;
    }
    // 实现 MsgAttachment 的接口，封装公用字段，然后调用子类的封装函数。
    @Override
    public String toJson(boolean send) {
        return fromJson(data);
    }

    // 子类的解析和封装接口。
//    protected abstract void parseData(JSONObject data);
//    protected abstract JSONObject packData();
}
