package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.model.LoginInfo;
import com.qiqi.springboot.seed.bz1.contract.service.LoginService;
import com.qiqi.springboot.seed.bz1.service.datamappers.UserMapper;
import com.qiqi.springboot.seed.bz1.service.entity.UserEntity;
import com.qiqi.springboot.seed.bz1.service.repository.UserRepository;
import com.qiqi.springboot.seed.common.exception.BusinessException;
import com.qiqi.springboot.seed.common.exception.ResultStatus;
import com.qiqi.springboot.seed.common.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-19 13:06
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Value("${app.check-token-timeout}")
    private int checkTokenTimeout;


    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 登录
     *
     * @param loginName
     * @param password
     * @return
     */
    @Override
    public LoginInfo login(String loginName, String password) {
        List<UserEntity> users = userRepository.findByUserName(loginName);
        if (CollectionUtils.isEmpty(users)) {
            throw new BusinessException(ResultStatus.DATA_NOT_EXIST);
        }
        if (!users.get(0).getPassword().equals(password)) {
            throw new BusinessException(ResultStatus.PARAM_IS_INVALID);
        }
        String token = UUID.randomUUID().toString();
        LoginInfo res = new LoginInfo();
        res.setToken(token);
        res.setUserInfo(userMapper.entityToModel(users.get(0)));
        // 删除之前的token，更新token
        Object oldTokenObj = redisUtil.get(res.getUserInfo().getId());
        if (null != oldTokenObj) {
            redisUtil.del(oldTokenObj.toString());
        }
        redisUtil.set(res.getUserInfo().getId(), token, checkTokenTimeout);
        redisUtil.set(token, res.getUserInfo().getId(), checkTokenTimeout);
        return res;
    }
}
