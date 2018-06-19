package com.android.similarwx.inteface;

import com.android.similarwx.beans.response.RspInMoney;

/**
 * Created by hanhuailong on 2018/6/19.
 */

public interface InMoneyViewInterface extends ViewInterface{
    void refreshInMoney(RspInMoney.InMoneyBean bean);
}
