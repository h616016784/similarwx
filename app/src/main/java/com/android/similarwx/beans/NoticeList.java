package com.android.similarwx.beans;

import java.util.List;

/**
 * Created by hanhuailong on 2018/5/28.
 */

public class NoticeList {
    private Integer count;
    private List<Notice> list;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Notice> getList() {
        return list;
    }

    public void setList(List<Notice> list) {
        this.list = list;
    }
}
