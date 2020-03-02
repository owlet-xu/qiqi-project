package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.model.*;
import com.qiqi.springboot.seed.bz1.contract.service.UserService;
import com.qiqi.springboot.seed.bz1.service.datamappers.UserMapper;
import com.qiqi.springboot.seed.bz1.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xgy
 * @description
 * @date 2018/9/6
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<UserInfo> findAll() {
        return this.userMapper.entitiesToModels(userRepository.findAll());
    }
}
