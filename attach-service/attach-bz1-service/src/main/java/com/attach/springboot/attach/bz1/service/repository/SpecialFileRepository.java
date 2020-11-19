package com.attach.springboot.attach.bz1.service.repository;

import com.attach.springboot.attach.bz1.service.entity.FileEntity;

import java.util.List;

public interface SpecialFileRepository {

    FileEntity save(FileEntity fileEntity);

    FileEntity updateMetadataById(FileEntity fileEntity);

    List<FileEntity> saveAll(List<FileEntity> fileEntities);

    Long deleteByFileIdAndModule(String fileId, String module);

    FileEntity findByFileIdAndModule(String fileId, String module);

    List<FileEntity> findByModule(String module);

    Long deleteByModuleAndFileIds(String module, List<String> fileIds);
}
