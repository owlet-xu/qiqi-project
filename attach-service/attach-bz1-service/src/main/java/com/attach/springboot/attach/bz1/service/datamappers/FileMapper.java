package com.attach.springboot.attach.bz1.service.datamappers;

import com.attach.springboot.attach.bz1.contract.model.FileInfo;
import com.attach.springboot.attach.bz1.service.entity.FileEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileMapper {

    FileEntity modelToEntity(FileInfo fileInfo);

    FileInfo entityToModel(FileEntity fileEntity);

    List<FileEntity> modelsToEntities(List<FileInfo> fileInfos);

    List<FileInfo> entitiesToModels(List<FileEntity> fileEntities);
}
