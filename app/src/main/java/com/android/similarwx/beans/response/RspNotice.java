package com.android.similarwx.beans.response;

import com.android.similarwx.beans.NoticeList;


/**
 * Created by hanhuailong on 2018/5/28.
 */

public class RspNotice extends BaseResponse {
    private NoticeList data;

    public NoticeList getData() {
        return data;
    }

    public void setData(NoticeList data) {
        this.data = data;
    }
}
