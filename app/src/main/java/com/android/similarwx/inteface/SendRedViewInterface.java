package com.android.similarwx.inteface;

import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.response.RspGroupInfo;

/**
 * Created by hanhuailong on 2018/7/12.
 */

public interface SendRedViewInterface extends ViewInterface {
    void reFreshSendRed(RspGroupInfo.GroupInfo groupInfo);
}
