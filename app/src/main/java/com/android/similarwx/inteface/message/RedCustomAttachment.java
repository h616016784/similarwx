package com.android.similarwx.inteface.message;

import com.alibaba.fastjson.JSONObject;
import com.android.similarwx.beans.SendRed;

/**
 * Created by hanhuailong on 2018/7/11.
 */

public class RedCustomAttachment extends CustomAttachment {
    public SendRed.SendRedBean getSendRedBean() {
        return sendRedBean;
    }

    public void setSendRedBean(SendRed.SendRedBean sendRedBean) {
        this.sendRedBean = sendRedBean;
    }

    private SendRed.SendRedBean sendRedBean;

    public RedCustomAttachment() {
        super(7);
    }

    @Override
    protected void parseData(JSONObject data) {
        String dataJson=data.toJSONString();
        sendRedBean=JSONObject.parseObject(dataJson, SendRed.SendRedBean.class);
    }

    @Override
    protected JSONObject packData() {
        if (sendRedBean!=null){
            String json=JSONObject.toJSONString(sendRedBean);
            JSONObject data=JSONObject.parseObject(json);
            return data;
        }
        return null;
    }
}
