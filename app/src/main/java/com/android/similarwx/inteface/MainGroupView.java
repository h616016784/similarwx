package com.android.similarwx.inteface;

import android.view.View;

import com.android.similarwx.beans.GroupMemberBean;
import com.android.similarwx.beans.GroupMessageBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public interface MainGroupView extends ViewInterface {
    void groupRefresh(List<GroupMessageBean> data);
}
