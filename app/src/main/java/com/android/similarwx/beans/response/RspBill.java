package com.android.similarwx.beans.response;

import com.android.similarwx.beans.Bill;

/**
 * Created by Administrator on 2018/6/12.
 */

public class RspBill extends BaseResponse {
    private Bill data;

    public Bill getData() {
        return data;
    }

    public void setData(Bill data) {
        this.data = data;
    }
}
