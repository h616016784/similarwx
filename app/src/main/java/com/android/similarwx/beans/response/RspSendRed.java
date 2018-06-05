package com.android.similarwx.beans.response;

import com.android.similarwx.beans.SendRed;

/**
 * Created by hanhuailong on 2018/6/5.
 */

public class RspSendRed extends BaseResponse {
    private SendRed data;

    public SendRed getData() {
        return data;
    }

    public void setData(SendRed data) {
        this.data = data;
    }
}
