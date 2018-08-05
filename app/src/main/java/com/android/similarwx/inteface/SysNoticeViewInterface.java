package com.android.similarwx.inteface;

import com.android.similarwx.beans.Notice;

import java.util.List;

/**
 * Created by Administrator on 2018/7/14.
 */

public interface SysNoticeViewInterface extends ViewInterface {
    void refreshSysNotice(List<Notice> list);
    void refreshSysMoney(String url);
    void refreshSysConfig();
}
