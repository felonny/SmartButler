package com.yuchen.smartbutler.entity;

import cn.bmob.v3.BmobUser;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.entity
 * 文件名: MyUser
 * Created by tangyuchen on 18/4/27.
 * 描述: 用户登录
 */

public class MyUser extends BmobUser {

    private int age;
    private boolean sex;
    private String desc;

    public int getAge() {
        return age;
    }

    public boolean isSex() {
        return sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
