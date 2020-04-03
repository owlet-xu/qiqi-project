package com.qiqi.springboot.seed.bz1.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import com.qiqi.springboot.seed.bz1.service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;

/**
 * @author xgy
 * @description 表单repository
 * @date 2018/9/6
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {
    /**
     * 根据Specification查询分页数据
     *
     * @param spec     Specification
     * @param pageable 分数参数
     * @return Page<T>
     */
    Page<UserEntity> findAll(@Nullable Specification<UserEntity> spec, Pageable pageable);

    List<UserEntity> findByUserName(String userName);

    UserEntity findByIdAndEnable(String id, int enable);

    @Query("update UserEntity q set q.enable=:enable where q.id=:id")
    @Modifying(clearAutomatically = true)
    int disableUser(@Param("id") String id, @Param("enable") Integer enable);

    @Query("update UserEntity u set u.password=:password where u.id=:id")
    @Modifying(clearAutomatically = true)
    int setUserPassword(@Param("id") String id, @Param("password") String password);
}
