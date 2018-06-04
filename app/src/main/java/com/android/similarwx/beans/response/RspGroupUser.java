package com.android.similarwx.beans.response;

import com.android.similarwx.beans.GroupUser;

import java.util.List;

/**
 * Created by hanhuailong on 2018/6/4.
 */

public class RspGroupUser extends BaseResponse {
    private GroupUser data;

    public GroupUser getData() {
        return data;
    }

    public void setData(GroupUser data) {
        this.data = data;
    }
}
