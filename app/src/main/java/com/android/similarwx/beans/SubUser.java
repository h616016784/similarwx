package com.android.similarwx.beans;

/**
 * Created by Administrator on 2018/7/14.
 */

public class SubUser extends BaseBean {
    private String userId;
    private String level;
    private String amount;
    private String returnAmout;
    private String userInfo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReturnAmout() {
        return returnAmout;
    }

    public void setReturnAmout(String returnAmout) {
        this.returnAmout = returnAmout;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}
