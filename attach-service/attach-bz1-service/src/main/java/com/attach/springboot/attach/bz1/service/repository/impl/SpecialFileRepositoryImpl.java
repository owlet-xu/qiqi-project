package com.attach.springboot.attach.bz1.service.repository.impl;

import com.attach.springboot.attach.bz1.service.entity.FileEntity;
import com.attach.springboot.attach.bz1.service.repository.SpecialFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class SpecialFileRepositoryImpl implements SpecialFileRepository {

    public static final String ID = "_id";
    public static final String FILE_ID = "fileId";
    public static final String MODULE = "module";
    public static final String SYSTEM = "system";
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public FileEntity save(FileEntity fileEntity) {
        Assert.notNull(fileEntity, "Entity must not be null!");

        String collectionName = getCollectionNameByModule(fileEntity.getModule());
        mongoTemplate.insert(fileEntity, collectionName);
        return fileEntity;
    }

    @Override
    public FileEntity updateMetadataById(FileEntity fileEntity) {
        String module = fileEntity == null ? null : fileEntity.getModule();
        String collectionName = getCollectionNameByModule(module);
        Update update = new Update();
        if (null != fileEntity.getRemark()) {
            update.set("remark", fileEntity.getRemark());
        }
        if (null != fileEntity.getSystem()) {
            update.set("system", fileEntity.getSystem());
        }
        if (null != fileEntity.getBusinessId()) {
            update.set("businessId", fileEntity.getBusinessId());
        }
        if (null != fileEntity.getModule()) {
            update.set("module", fileEntity.getModule());
        }
        mongoTemplate.updateFirst(query(where(ID).is(fileEntity.getId())), update, FileEntity.class, collectionName);
        return fileEntity;
    }

    @Override
    public List<FileEntity> saveAll(List<FileEntity> fileEntities) {
        Assert.notNull(fileEntities, "The given fileEntities not be null!");

        FileEntity fileEntity = fileEntities.stream().findFirst().orElse(null);
        String module = fileEntity == null ? null : fileEntity.getModule();
        String collectionName = getCollectionNameByModule(module);
        mongoTemplate.insert(fileEntities, collectionName);
        return fileEntities;
    }

    @Override
    public Long deleteByFileIdAndModule(String fileId, String module) {
        Assert.notNull(fileId, "The given fileId must not be null!");

        String collectionName = getCollectionNameByModule(module);
        return mongoTemplate.remove(query(where(FILE_ID).is(fileId)), collectionName).getDeletedCount();
    }

    @Override
    public FileEntity findByFileIdAndModule(String fileId, String module) {
        Assert.notNull(fileId, "The given fileId must not be null!");

        String collectionName = getCollectionNameByModule(module);
        return mongoTemplate.findOne(query(where(FILE_ID).is(fileId)), FileEntity.class, collectionName);
    }

    @Override
    public List<FileEntity> findByModule(String module) {
        Assert.notNull(module, "The given module must not be null!");

        String collectionName = getCollectionNameByModule(module);
        return mongoTemplate.find(query(where(MODULE).is(module)), FileEntity.class, collectionName);
    }

    @Override
    public List<FileEntity> findBySystem(String system) {
        Assert.notNull(system, "The given system must not be null!");

        String collectionName = getCollectionNameByModule(system);
        return mongoTemplate.find(query(where(SYSTEM).is(system)), FileEntity.class, collectionName);
    }

    @Override
    public Long deleteByModuleAndFileIds(String module, List<String> fileIds) {
        Assert.notNull(fileIds, "The given fileIds must not be null!");

        String collectionName = getCollectionNameByModule(module);
        return mongoTemplate.remove(query(where(FILE_ID).in(fileIds)), collectionName).getDeletedCount();
    }

    /**
     * 根据模块名获取对应的文件集合名称
     *
     * @param module 模块名
     * @return 文件结合名称
     */
    public String getCollectionNameByModule(String module) {
        return "b_file";
    }
}
