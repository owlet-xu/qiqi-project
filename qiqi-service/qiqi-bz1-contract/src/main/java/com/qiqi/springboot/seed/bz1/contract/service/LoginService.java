package com.qiqi.springboot.seed.bz1.contract.service;

import com.qiqi.springboot.seed.bz1.contract.model.LoginInfo;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-19 13:05
 */
public interface LoginService {

    /**
     * 登录
     * @param loginName
     * @param password
     * @return
     */
    LoginInfo login(String loginName, String password);
}
