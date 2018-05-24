package com.android.similarwx.beans.response;

import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.User;

import java.util.List;

/**
 * Created by Administrator on 2018/5/22.
 */

public class RspGroup extends BaseResponse {
    List<GroupMessageBean> data;

    public List<GroupMessageBean> getData() {
        return data;
    }

    public void setData(List<GroupMessageBean> data) {
        this.data = data;
    }
}
