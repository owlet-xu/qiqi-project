package com.qiqi.springboot.seed.bz1.service.repository;

import com.qiqi.springboot.seed.bz1.service.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 11:48
 */
public interface RoleRepository  extends JpaRepository<RoleEntity, String> {
}
