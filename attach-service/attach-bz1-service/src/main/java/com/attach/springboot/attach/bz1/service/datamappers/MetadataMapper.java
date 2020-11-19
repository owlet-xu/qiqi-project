package com.attach.springboot.attach.bz1.service.datamappers;


import com.attach.springboot.attach.bz1.contract.model.MetadataInfo;
import com.attach.springboot.attach.bz1.service.entity.MetadataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MetadataMapper {

    @Mapping(ignore = true, target = "remark")
    MetadataEntity modelToEntity(MetadataInfo metadataInfo);

    MetadataInfo entityToModel(MetadataEntity metadataEntity);

    List<MetadataEntity> modelsToEntities(List<MetadataInfo> metadataInfos);

    List<MetadataInfo> entitiesToModels(List<MetadataEntity> metadataEntities);

}
