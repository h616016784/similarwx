package com.android.similarwx.beans.response;

import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.User;

import java.util.List;

/**
 * Created by Administrator on 2018/5/22.
 */

public class RspGroup extends BaseResponse {
    GroupMessageBean data;

    public GroupMessageBean getData() {
        return data;
    }

    public void setData(GroupMessageBean data) {
        this.data = data;
    }
}
