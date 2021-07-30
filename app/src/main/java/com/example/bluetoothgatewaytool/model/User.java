package com.example.bluetoothgatewaytool.model;

import org.litepal.crud.DataSupport;

/**
 * @author 章可政
 * @date 2021/7/20 11:45
 */
public class User extends DataSupport {

    private int id;

    private String username;

    private String password;

    private boolean isLogin;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
