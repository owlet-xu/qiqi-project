package com.qiqi.springboot.seed.bz1.service.datamappers.goods;

import com.qiqi.springboot.seed.bz1.contract.model.goods.OrdersInfo;
import com.qiqi.springboot.seed.bz1.service.entity.goods.OrdersEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-23 09:16
 */
@Mapper(componentModel = "spring")
public interface OrdersMapper {
    OrdersInfo entityToModel(OrdersEntity formEntity);

    OrdersEntity modelToEntity(OrdersInfo formInfo);

    List<OrdersInfo> entitiesToModels(List<OrdersEntity> formEntity);

    List<OrdersEntity> modelsToEntities(List<OrdersInfo> formInfo);
}
