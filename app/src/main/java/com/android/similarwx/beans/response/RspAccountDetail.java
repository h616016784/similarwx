package com.android.similarwx.beans.response;

import com.android.similarwx.beans.AccountDetailBean;
import com.android.similarwx.beans.Bill;

/**
 * Created by hanhuailong on 2018/9/4.
 */

public class RspAccountDetail extends BaseResponse{
    private AccountDetailBean data;

    public AccountDetailBean getData() {
        return data;
    }

    public void setData(AccountDetailBean data) {
        this.data = data;
    }
}
