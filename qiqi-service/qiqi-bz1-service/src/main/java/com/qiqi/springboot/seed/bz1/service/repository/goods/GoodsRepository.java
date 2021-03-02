package com.qiqi.springboot.seed.bz1.service.repository.goods;

import com.qiqi.springboot.seed.bz1.service.entity.goods.GoodsEntity;
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
public interface GoodsRepository extends JpaRepository<GoodsEntity, String> {
    /**
     * 根据Specification查询分页数据
     *
     * @param spec     Specification
     * @param pageable 分数参数，注意顺序不能动，实体中有映射
     * @return Page<T>
     */
    @Query("select g.id, g.name, g.price, g.description, g.updateTime, g.createTime, g.enable from GoodsEntity g")
    Page<Object[]> findAll(@Nullable Specification<GoodsEntity> spec, Pageable pageable);

    @Query("select g.detail, g.detailBg, g.file1, g.file2, g.file3, g.file4, g.file5, g.file6 from GoodsEntity g where g.detail is not null")
    @Modifying(clearAutomatically = true)
    List<Object[]> getImages();

    @Query("update GoodsEntity g set g.enable=:enable where g.id=:id")
    @Modifying(clearAutomatically = true)
    int disableGoods(@Param("id") String id, @Param("enable") Integer enable);
}
