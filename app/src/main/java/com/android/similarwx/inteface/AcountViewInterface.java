package com.android.similarwx.inteface;

import com.android.similarwx.beans.Bill;

/**
 * Created by hanhuailong on 2018/6/15.
 */

public interface AcountViewInterface extends ViewInterface {
    void refreshBill(Bill bill);
}
