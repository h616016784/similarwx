package com.android.similarwx.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hanhuailong on 2018/3/16.
 */

@Entity
public class DbUser {
    @Id
    private Long id;
    private String name;

    @Generated(hash = 1013153607)
    public DbUser(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 762027100)
    public DbUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DbUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
