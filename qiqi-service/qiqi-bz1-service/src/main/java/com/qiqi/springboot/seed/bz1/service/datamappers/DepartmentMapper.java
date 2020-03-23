package com.qiqi.springboot.seed.bz1.service.datamappers;

import com.qiqi.springboot.seed.bz1.contract.model.DepartmentInfo;
import com.qiqi.springboot.seed.bz1.service.entity.DepartmentEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-23 09:16
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentInfo entityToModel(DepartmentEntity formEntity);

    DepartmentEntity modelToEntity(DepartmentInfo formInfo);

    List<DepartmentInfo> entitiesToModels(List<DepartmentEntity> formEntity);

    List<DepartmentEntity> modelsToEntities(List<DepartmentInfo> formInfo);
}
