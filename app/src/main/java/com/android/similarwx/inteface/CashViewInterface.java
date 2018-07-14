package com.android.similarwx.inteface;

import com.android.similarwx.beans.response.RspCashUser;

/**
 * Created by Administrator on 2018/7/14.
 */

public interface CashViewInterface extends ViewInterface {
    void refreshCash(RspCashUser.CashUser cashUser);
}
