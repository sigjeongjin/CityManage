package com.citymanage;

/**
 * Created by we25 on 2017-06-26.
 */

public class WmItem {
    String name;
    String mobile;
    int age;
    int resId;

    public WmItem(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public WmItem(String name, String mobile, int age, int resId) {
        this.name = name;
        this.mobile = mobile;
        this.age = age;
        this.resId = resId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
