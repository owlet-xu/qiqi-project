package com.qiqi.springboot.seed.common.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2017/3/3.
 */
@ConfigurationProperties(prefix = "xseed")
public class XseedSettings {
    // 用户默认密码
    public static String defaultPassword = "123456";
    // 超级管理员的角色Id
    public static String adminRoleId = "e1dc85b2-f366-45ae-9499-1e6d515983ae";
    // http请求头的字段
    public static String authorizationHeadName = "Authorization";
    private String url1;
    private String url2;

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }
}
