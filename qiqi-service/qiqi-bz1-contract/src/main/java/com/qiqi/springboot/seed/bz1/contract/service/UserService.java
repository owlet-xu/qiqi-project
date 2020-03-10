package com.qiqi.springboot.seed.bz1.contract.service;

import com.qiqi.springboot.seed.bz1.contract.model.PageInfo;
import com.qiqi.springboot.seed.bz1.contract.model.UserInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020/3/2
 */
public interface UserService {
    PageInfo<UserInfo> findUserListPage(PageInfo<UserInfo> pageInfo);

    Boolean saveUser(UserInfo userInfo);
}
