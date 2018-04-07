package com.android.similarwx.beans;

/**
 * Created by Administrator on 2018/4/6.
 */

public class NoticeItemBean extends BaseBean {
    private String id;
    private String title;
    private String time;
    private String content;
    private String contentdetail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentdetail() {
        return contentdetail;
    }

    public void setContentdetail(String contentdetail) {
        this.contentdetail = contentdetail;
    }

    @Override
    public String toString() {
        return "NoticeItemBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", contentdetail='" + contentdetail + '\'' +
                '}';
    }
}
