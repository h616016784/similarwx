package com.android.similarwx.beans.response;

import com.android.similarwx.beans.RedDetailBean;

/**
 * Created by hanhuailong on 2018/5/30.
 */

public class RspRed extends BaseResponse{
    private RedDetailBean data;

    public RedDetailBean getData() {
        return data;
    }

    public void setData(RedDetailBean data) {
        this.data = data;
    }
}
