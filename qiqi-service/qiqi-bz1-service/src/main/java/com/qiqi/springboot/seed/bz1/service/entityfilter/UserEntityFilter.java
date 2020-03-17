package com.qiqi.springboot.seed.bz1.service.entityfilter;

import com.qiqi.springboot.seed.bz1.service.entity.UserEntity;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-16 17:21
 */
public class UserEntityFilter {
    private static UserEntityFilter instance;

    private UserEntityFilter() { }

    public static UserEntityFilter getInstance() {
        if (null == instance) {
            instance = new UserEntityFilter();
        }
        return instance;
    }
    public  UserEntity noPassword(UserEntity user) {
        user.setPassword(null);
        return user;
    }
}
