package com.qiqi.springboot.seed.bz1.service.datamappers;

import com.qiqi.springboot.seed.bz1.contract.model.MenuInfo;
import com.qiqi.springboot.seed.bz1.service.entity.MenuEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 13:58
 */
@Mapper(componentModel = "spring")
public interface MenuMapper {
    MenuInfo entityToModel(MenuEntity formEntity);

    MenuEntity modelToEntity(MenuInfo formInfo);

    List<MenuInfo> entitiesToModels(List<MenuEntity> formEntity);

    List<MenuEntity> modelsToEntities(List<MenuInfo> formInfo);
}
