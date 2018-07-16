package com.android.similarwx.inteface.message;

import com.alibaba.fastjson.JSONObject;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.Transfer;

/**
 * Created by hanhuailong on 2018/7/16.
 */

public class TransCustomAttachment extends CustomAttachment {
    private Transfer transfer;

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public TransCustomAttachment() {
        super(8);
    }

    @Override
    protected void parseData(JSONObject data) {
        String dataJson=data.toJSONString();
        transfer=JSONObject.parseObject(dataJson, Transfer.class);
    }

    @Override
    protected JSONObject packData() {
        if (transfer!=null){
            String json=JSONObject.toJSONString(transfer);
            JSONObject data=JSONObject.parseObject(json);
            return data;
        }
        return null;
    }
}
