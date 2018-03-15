package com.android.similarwx.beans;

/**
 * Created by hanhuailong on 2018/3/15.
 */

public class User {
    private String token;
    private String name;
    private int phone;

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