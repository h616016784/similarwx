package com.android.similarwx.beans;

/**
 * Created by Administrator on 2018/4/1.
 */

public class GroupMessageBean extends BaseBean {
    private String name;
    private String imageUrl;
    private String msgCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(String msgCount) {
        this.msgCount = msgCount;
    }
}
