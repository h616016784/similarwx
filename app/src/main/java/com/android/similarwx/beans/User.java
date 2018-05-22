package com.android.similarwx.beans;

/**
 * Created by hanhuailong on 2018/3/15.
 */

public class User extends BaseBean{
    private String token;
    private String name;
    private int phone;
    private String id;
    private String accid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                '}';
    }
}
