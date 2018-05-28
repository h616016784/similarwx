package com.android.similarwx.inteface;

import com.android.similarwx.beans.Notice;

import java.util.List;

/**
 * Created by hanhuailong on 2018/5/28.
 */

public interface NoticeViewInterface extends ViewInterface {
    void reFreshView(List<Notice> data);
}
