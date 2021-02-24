package com.qiqi.springboot.seed.bz1.service.repository.goods;

import com.qiqi.springboot.seed.bz1.service.entity.goods.GoodsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-23 09:17
 */
public interface GoodsRepository extends JpaRepository<GoodsEntity, String> {
    /**
     * 根据Specification查询分页数据
     *
     * @param spec     Specification
     * @param pageable 分数参数
     * @return Page<T>
     */
    Page<GoodsEntity> findAll(@Nullable Specification<GoodsEntity> spec, Pageable pageable);
}
