package com.android.similarwx.beans;

/**
 * Created by Administrator on 2018/4/14.
 */

public class RedTakeBean extends BaseBean{
    private String redType;
    private String money;
    private String time;

    public String getRedType() {
        return redType;
    }

    public void setRedType(String redType) {
        this.redType = redType;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "RedTakeBean{" +
                "redType='" + redType + '\'' +
                ", money='" + money + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
