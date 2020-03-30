package com.qiqi.springboot.seed.bz1.contract.service;

import com.qiqi.springboot.seed.bz1.contract.model.PageInfo;
import com.qiqi.springboot.seed.bz1.contract.model.UserInfo;

/**
 * @author xuguoyuan
 * @description
 * @date 2020/3/2
 */
public interface UserService {

    /**
     * 分页查找数据
     * @param pageInfo
     * @return
     */
    PageInfo<UserInfo> findUserListPage(PageInfo<UserInfo> pageInfo);

    /**
     * 保存用户
     * @param userInfo
     * @return
     */
    Boolean saveUser(UserInfo userInfo);

    /**
     * 删除用户
     * @param id
     * @return
     */
    Boolean disableUser(String id);
}
