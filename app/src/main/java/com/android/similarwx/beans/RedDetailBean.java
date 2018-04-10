package com.android.similarwx.beans;

/**
 * Created by hanhuailong on 2018/4/10.
 */

public class RedDetailBean extends BaseBean {
    private String name;
    private String url;
    private String money;
    private String time;
    private String shouqi;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getShouqi() {
        return shouqi;
    }

    public void setShouqi(String shouqi) {
        this.shouqi = shouqi;
    }

    @Override
    public String toString() {
        return "RedDetailBean{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", money='" + money + '\'' +
                ", time='" + time + '\'' +
                ", shouqi='" + shouqi + '\'' +
                '}';
    }
}
