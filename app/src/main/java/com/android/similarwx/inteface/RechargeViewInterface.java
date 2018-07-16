package com.android.similarwx.inteface;

import com.android.similarwx.beans.Transfer;
import com.android.similarwx.beans.response.RspTransfer;

/**
 * Created by Administrator on 2018/6/17.
 */

public interface RechargeViewInterface extends ViewInterface {
    void refreshRecharge(Transfer transfer);
}
