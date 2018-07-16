package com.android.similarwx.beans;

/**
 * Created by hanhuailong on 2018/7/16.
 */

public class Transfer extends BaseBean{
    private String name;
    private String amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
