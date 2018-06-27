package com.android.similarwx.beans.response;

import com.android.similarwx.beans.SendRed;

/**
 * Created by hanhuailong on 2018/6/5.
 */

public class RspSendRed extends BaseResponse {
    private SendRed.SendRedBean data;

    public SendRed.SendRedBean getData() {
        return data;
    }

    public void setData(SendRed.SendRedBean data) {
        this.data = data;
    }
}
