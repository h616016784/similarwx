package com.android.similarwx.misdk.model;

import com.android.similarwx.beans.RedDetailBean;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachmentParser;

/**
 * Created by hanhuailong on 2018/5/30.
 */

public class RedCustomAttachParser implements MsgAttachmentParser {
    @Override
    public MsgAttachment parse(String json) {
        CustomAttachment<RedDetailBean> attachment=null;
        try {
            Gson gson=new Gson();
            RedDetailBean redDetailBean= gson.fromJson(json,RedDetailBean.class);
            attachment=new CustomAttachment<RedDetailBean>(redDetailBean);
        }catch (Exception e){
            e.printStackTrace();
        }
        return attachment;
    }
}
