package com.android.similarwx.beans.response;

import com.android.similarwx.beans.BaseBean;

/**
 * Created by hanhuailong on 2018/6/19.
 */

public class RspCanGrab extends BaseResponse{
    private BaseBean data;

    public BaseBean getData() {
        return data;
    }

    public void setData(BaseBean data) {
        this.data = data;
    }
}
