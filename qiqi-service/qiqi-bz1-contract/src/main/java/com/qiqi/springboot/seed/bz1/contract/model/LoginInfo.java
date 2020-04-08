package com.qiqi.springboot.seed.bz1.contract.model;

/**
 * @author xuguoyuan
 * @description 登录返回model
 * @date 2020-03-16 15:17
 */
public class LoginInfo {
    private String token;
    private UserInfo userInfo;
    private String admin; // 加密后的token

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
