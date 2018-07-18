package com.bhm.sdk.greendao.demo.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by bhm on 2018/7/18.
 */
@Entity
@GreenDaoEx
public class TestEntity {

    //不能用int
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String username;//帐号
    @Property(nameInDb = "password")
    private String password;//密码
}
