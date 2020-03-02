package com.qiqi.springboot.seed.bz1.service.repository;

import com.qiqi.springboot.seed.bz1.service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xgy
 * @description 表单repository
 * @date 2018/9/6
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {
}
