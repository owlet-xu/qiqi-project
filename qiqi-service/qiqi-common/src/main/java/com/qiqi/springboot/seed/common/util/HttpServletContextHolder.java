package com.qiqi.springboot.seed.common.util;

import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-18 15:34
 */
public class HttpServletContextHolder extends RequestContextHolder {
    /**
     * 获取当前请求
     *
     * @return 当前请求
     */
    public static HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = currentRequestAttributes();
        Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    /**
     * 从当前请求中获取header
     * @param name header名称
     * @return header值
     */
    public static String getHeader(String name) {
        HttpServletRequest httpServletRequest = getCurrentRequest();
        return httpServletRequest.getHeader(name);
    }
}
