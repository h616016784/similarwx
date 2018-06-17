package com.android.similarwx.beans;

/**
 * Created by hanhuailong on 2018/4/10.
 */

public class PopMoreBean extends BaseBean{
    private String name;
    private String content;
    private int image;
    private String id;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PopMoreBean{" +
                "name='" + name + '\'' +
                ", image=" + image +
                ", id='" + id + '\'' +
                '}';
    }
}
