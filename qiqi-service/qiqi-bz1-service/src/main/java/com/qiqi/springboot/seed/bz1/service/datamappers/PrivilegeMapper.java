package com.qiqi.springboot.seed.bz1.service.datamappers;

import com.qiqi.springboot.seed.bz1.contract.model.PrivilegeInfo;
import com.qiqi.springboot.seed.bz1.service.entity.PrivilegeEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 14:02
 */
@Mapper(componentModel = "spring")
public interface PrivilegeMapper {
    PrivilegeInfo entityToModel(PrivilegeEntity formEntity);

    PrivilegeEntity modelToEntity(PrivilegeInfo formInfo);

    List<PrivilegeInfo> entitiesToModels(List<PrivilegeEntity> formEntity);

    List<PrivilegeEntity> modelsToEntities(List<PrivilegeInfo> formInfo);
}
