package com.qiqi.springboot.seed.bz1.service.repository;

import com.qiqi.springboot.seed.bz1.service.entity.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 13:07
 */
public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, String> {
}
