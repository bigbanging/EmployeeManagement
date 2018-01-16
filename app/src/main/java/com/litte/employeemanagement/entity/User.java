package com.litte.employeemanagement.entity;

/**
 * Created by litte on 2017/12/26.
 */

public class User {
    private int id;
    private String loginName;
    private String password;
    private String realName;
    private String email;

    public User() {
    }

    public User(int id, String loginName, String password, String realName, String email) {
        this.id = id;
        this.loginName = loginName;
        this.password = password;
        this.realName = realName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
