package com.android.similarwx.beans.response;

import com.android.similarwx.beans.User;

/**
 * Created by Administrator on 2018/5/22.
 */

public class RspUser extends BaseResponse {
    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
