package com.android.similarwx.beans.response;

import com.android.similarwx.beans.Transfer;

/**
 * Created by Administrator on 2018/6/17.
 */

public class RspTransfer extends BaseResponse{
    private Transfer data;

    public Transfer getData() {
        return data;
    }

    public void setData(Transfer data) {
        this.data = data;
    }
}
