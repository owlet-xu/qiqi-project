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

    /**
     * 验证用户密码是否正确
     * @param token
     * @param passwordMd5
     * @return
     */
    boolean newPassword(String token, String passwordMd5);

    /**
     * 设置用户新密码
     * @param token
     * @param passwordMd5
     * @return
     */
    boolean validePassword(String token, String passwordMd5);

    /**
     * 重置用户密码
     * @param token
     * @return
     */
    boolean resetPassword(String token);
}
