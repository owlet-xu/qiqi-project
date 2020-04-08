package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.model.LoginInfo;
import com.qiqi.springboot.seed.bz1.contract.model.RoleInfo;
import com.qiqi.springboot.seed.bz1.contract.model.UserInfo;
import com.qiqi.springboot.seed.bz1.contract.service.LoginService;
import com.qiqi.springboot.seed.bz1.contract.service.UserService;
import com.qiqi.springboot.seed.bz1.service.datamappers.UserMapper;
import com.qiqi.springboot.seed.bz1.service.entity.UserEntity;
import com.qiqi.springboot.seed.bz1.service.repository.UserRepository;
import com.qiqi.springboot.seed.common.configs.XseedSettings;
import com.qiqi.springboot.seed.common.exception.BusinessException;
import com.qiqi.springboot.seed.common.exception.ResultStatus;
import com.qiqi.springboot.seed.common.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-19 13:06
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements LoginService {

    @Value("${app.check-token-timeout}")
    private int checkTokenTimeout;


    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserService userService;

    /**
     * 登录
     *
     * @param loginName
     * @param password
     * @return
     */
    @Override
    public LoginInfo login(String loginName, String password) {
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
            throw new BusinessException(ResultStatus.PARAM_IS_INVALID);
        }
        List<UserEntity> users = userRepository.findByUserName(loginName);
        if (CollectionUtils.isEmpty(users)) {
            throw new BusinessException(ResultStatus.DATA_NOT_EXIST);
        }
        if (!password.equals(users.get(0).getPassword())) {
            throw new BusinessException(ResultStatus.PARAM_IS_INVALID);
        }
        String token = UUID.randomUUID().toString();
        UserInfo userInfo = userMapper.entityToModel(users.get(0));
        // 加上角色信息
        userService.addRoleInfo(userInfo);
        LoginInfo res = new LoginInfo();
        res.setToken(token);
        res.setUserInfo(userInfo);
        // 判断是否有admin
        if (!CollectionUtils.isEmpty( userInfo.getRoleInfos())) {
            List<String> roleIds = userInfo.getRoleInfos().stream().map(RoleInfo::getId).collect(Collectors.toList());
            res.setAdmin(checkAdmin(roleIds) ? token : null);
        }
        // 删除之前的token，更新token
        Object oldTokenObj = redisUtil.get(res.getUserInfo().getId());
        if (null != oldTokenObj) {
            redisUtil.del(oldTokenObj.toString());
        }
        redisUtil.set(res.getUserInfo().getId(), token, checkTokenTimeout);
        redisUtil.set(token, res.getUserInfo().getId(), checkTokenTimeout);
        return res;
    }

    /**
     * 通过token获取登录信息
     *
     * @param token
     * @return
     */
    @Override
    public LoginInfo getLoginInfo(String token) {
        UserEntity userEntity = userRepository.findById(getUserIdByToken(token)).orElse(null);
        if (null == userEntity) {
            throw new BusinessException(ResultStatus.DATA_NOT_EXIST);
        }
        UserInfo userInfo = userMapper.entityToModel(userEntity);
        // 加上角色信息
        userService.addRoleInfo(userInfo);
        LoginInfo res = new LoginInfo();
        res.setToken(token);
        res.setUserInfo(userInfo);
        return res;
    }

    /**
     * 设置用户新密码
     *
     * @param token
     * @param passwordMd5
     * @return
     */
    @Override
    public boolean newPassword(String token, String passwordMd5) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(passwordMd5)) {
            throw new BusinessException(ResultStatus.PARAM_IS_INVALID);
        }
        // 设置用户新密码
        userRepository.setUserPassword(getUserIdByToken(token), passwordMd5);
        return true;
    }

    /**
     * 验证用户密码是否正确
     *
     * @param token
     * @param passwordMd5
     * @return
     */
    @Override
    public boolean validePassword(String token, String passwordMd5) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(passwordMd5)) {
            throw new BusinessException(ResultStatus.PARAM_IS_INVALID);
        }
        // 获取用户的密码
        UserEntity user = userRepository.findByIdAndEnable(getUserIdByToken(token), 1);
        return passwordMd5.equals(user.getPassword()) ? true : false;
    }

    /**
     * 重置用户密码
     *
     * @param token
     * @return
     */
    @Override
    public boolean resetPassword(String token) {
        // 重置用户密码
        userRepository.setUserPassword(getUserIdByToken(token), XseedSettings.defaultPassword);
        return true;
    }

    private String getUserIdByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(ResultStatus.PARAM_IS_INVALID);
        }
        Object userIdO = redisUtil.get(token);
        if (null == userIdO) {
            throw new BusinessException(ResultStatus.DATA_NOT_EXIST);
        }
        return (String) userIdO;
    }

    /**
     * 是否是超级管理员
     *
     * @param token
     * @return
     */
    @Override
    public boolean checkAdmin(String token) {
        List<String> roleIds = userService.getUserRoleIds(getUserIdByToken(token));
        return checkAdmin(roleIds);
    }

    private boolean checkAdmin(List<String> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return false;
        }
        return roleIds.contains(XseedSettings.adminRoleId);
    }
}
