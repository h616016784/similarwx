package com.android.similarwx.beans;

/**
 * Created by hanhuailong on 2018/9/4.
 */

public class AccountDetailBean extends BaseBean{
    private  String value;
    private  String type;
    private  String oppositeId;

    public String getOppositeId() {
        return oppositeId;
    }

    public void setOppositeId(String oppositeId) {
        this.oppositeId = oppositeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
