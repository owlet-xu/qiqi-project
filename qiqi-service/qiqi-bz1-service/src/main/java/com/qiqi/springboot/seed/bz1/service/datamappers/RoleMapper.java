package com.qiqi.springboot.seed.bz1.service.datamappers;

import com.qiqi.springboot.seed.bz1.contract.model.RoleInfo;
import com.qiqi.springboot.seed.bz1.service.entity.RoleEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 14:00
 */
@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleInfo entityToModel(RoleEntity formEntity);

    RoleEntity modelToEntity(RoleInfo formInfo);

    List<RoleInfo> entitiesToModels(List<RoleEntity> formEntity);

    List<RoleEntity> modelsToEntities(List<RoleInfo> formInfo);
}
