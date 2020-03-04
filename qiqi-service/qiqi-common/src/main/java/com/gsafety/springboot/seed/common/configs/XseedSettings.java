package com.qiqi.springboot.seed.common.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2017/3/3.
 */
@ConfigurationProperties(prefix = "xseed")
public class XseedSettings {
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