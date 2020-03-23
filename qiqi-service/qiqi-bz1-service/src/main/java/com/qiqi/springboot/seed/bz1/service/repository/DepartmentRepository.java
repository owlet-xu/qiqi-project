package com.qiqi.springboot.seed.bz1.service.repository;

import com.qiqi.springboot.seed.bz1.service.entity.DepartmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-23 09:17
 */
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, String> {
    /**
     * 根据Specification查询分页数据
     *
     * @param spec     Specification
     * @param pageable 分数参数
     * @return Page<T>
     */
    Page<DepartmentEntity> findAll(@Nullable Specification<DepartmentEntity> spec, Pageable pageable);

    List<DepartmentEntity> findByParentId(String parentId);

    @Query("update DepartmentEntity set enable=:enable where id in :ids")
    @Modifying(clearAutomatically = true)
    int disableDepartment(@Param("ids") List<String> ids, @Param("enable") Integer enable);
}
