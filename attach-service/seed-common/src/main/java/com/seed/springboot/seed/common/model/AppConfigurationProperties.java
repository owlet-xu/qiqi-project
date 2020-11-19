package com.seed.springboot.seed.common.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-18 16:05
 */
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfigurationProperties {
    /**
     * 是否开启token检查
     */
    private boolean checkToken;

    /**
     * 检查token url
     */
    private String checkTokenUrl;

    /**
     * rest template 连接超时时间
     */
    private int restConnectTimeout;

    /**
     * rest template 读取超时时间
     */
    private int restReadTimeout;

    public boolean isCheckToken() {
        return checkToken;
    }

    public void setCheckToken(boolean checkToken) {
        this.checkToken = checkToken;
    }

    public String getCheckTokenUrl() {
        return checkTokenUrl;
    }

    public void setCheckTokenUrl(String checkTokenUrl) {
        this.checkTokenUrl = checkTokenUrl;
    }

    public int getRestConnectTimeout() {
        return restConnectTimeout;
    }

    public void setRestConnectTimeout(int restConnectTimeout) {
        this.restConnectTimeout = restConnectTimeout;
    }

    public int getRestReadTimeout() {
        return restReadTimeout;
    }

    public void setRestReadTimeout(int restReadTimeout) {
        this.restReadTimeout = restReadTimeout;
    }
}
