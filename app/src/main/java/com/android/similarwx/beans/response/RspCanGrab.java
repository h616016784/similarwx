package com.android.similarwx.beans.response;

import com.android.similarwx.beans.BaseBean;
import com.android.similarwx.beans.CanGrabBean;

/**
 * Created by hanhuailong on 2018/6/19.
 */

public class RspCanGrab extends BaseResponse{
    private CanGrabBean data;

    public CanGrabBean getData() {
        return data;
    }

    public void setData(CanGrabBean data) {
        this.data = data;
    }
}
