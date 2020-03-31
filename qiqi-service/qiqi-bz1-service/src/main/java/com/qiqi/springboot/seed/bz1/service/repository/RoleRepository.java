package com.qiqi.springboot.seed.bz1.service.repository;

import com.qiqi.springboot.seed.bz1.service.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 11:48
 */
public interface RoleRepository  extends JpaRepository<RoleEntity, String> {


    @Query("update RoleEntity r set r.enable=:enable where r.id=:id")
    @Modifying(clearAutomatically = true)
    int disableRole(@Param("id") String id, @Param("enable") Integer enable);
}
