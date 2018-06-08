package com.android.similarwx.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/31.
 */

public class BaseBean implements Serializable{
    private String retCode;
    private String retMsg;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
}
