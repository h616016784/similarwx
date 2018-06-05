package com.android.similarwx.beans;

/**
 * Created by hanhuailong on 2018/6/5.
 */

public class SendRed extends BaseBean{
    private String redPacId;
    private String name;
    private String url;
    private String amount;
    private String time;
    private String shouqi;
    private String requestNum;
    private String userId;
    private String groupId;
    private String type;

    public String getRedPacId() {
        return redPacId;
    }

    public void setRedPacId(String redPacId) {
        this.redPacId = redPacId;
    }

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getRequestNum() {
        return requestNum;
    }

    public void setRequestNum(String requestNum) {
        this.requestNum = requestNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
