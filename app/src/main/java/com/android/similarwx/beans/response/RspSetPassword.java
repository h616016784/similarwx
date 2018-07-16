package com.android.similarwx.beans.response;

import com.android.similarwx.beans.User;

/**
 * Created by hanhuailong on 2018/7/6.
 */

public class RspSetPassword extends BaseResponse{
    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
