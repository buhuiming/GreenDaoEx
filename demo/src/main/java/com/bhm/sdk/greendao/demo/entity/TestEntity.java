package com.bhm.sdk.greendao.demo.entity;

import com.bhm.sdk.greendao.Parcelable;

/**
 * Created by bhm on 2018/7/18.
 */
@Parcelable
public class TestEntity {

    private Long id;
    private String username;//帐号
    private String password;//密码

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
