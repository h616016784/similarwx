package com.android.similarwx.inteface;

import com.android.similarwx.beans.RedDetailBean;
import com.android.similarwx.beans.RedDetialBean;
import com.android.similarwx.beans.response.RspRedDetail;

import java.util.List;

/**
 * Created by hanhuailong on 2018/6/19.
 */

public interface RedDetailViewInterface extends ViewInterface {
    void refreshRedDetail(RspRedDetail.RedListData list);
}
