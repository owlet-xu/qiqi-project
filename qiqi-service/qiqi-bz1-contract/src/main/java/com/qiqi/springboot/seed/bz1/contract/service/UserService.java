package com.qiqi.springboot.seed.bz1.contract.service;

import com.qiqi.springboot.seed.bz1.contract.model.UserInfo;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020/3/2
 */
public interface UserService {
    List<UserInfo> findAll();
}
