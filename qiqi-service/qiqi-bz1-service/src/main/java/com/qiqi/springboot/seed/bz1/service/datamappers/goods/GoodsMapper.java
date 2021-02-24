package com.qiqi.springboot.seed.bz1.service.datamappers.goods;

import com.qiqi.springboot.seed.bz1.contract.model.goods.GoodsInfo;
import com.qiqi.springboot.seed.bz1.service.entity.goods.GoodsEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-23 09:16
 */
@Mapper(componentModel = "spring")
public interface GoodsMapper {
    GoodsInfo entityToModel(GoodsEntity formEntity);

    GoodsEntity modelToEntity(GoodsInfo formInfo);

    List<GoodsInfo> entitiesToModels(List<GoodsEntity> formEntity);

    List<GoodsEntity> modelsToEntities(List<GoodsInfo> formInfo);
}
