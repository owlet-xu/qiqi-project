package com.qiqi.springboot.seed.bz1.service.datamappers;

import com.qiqi.springboot.seed.bz1.contract.model.UserInfo;
import com.qiqi.springboot.seed.bz1.service.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author xgy
 * @description 表单Mapper
 * @date 2018/9/6
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserInfo entityToModel(UserEntity formEntity);

    UserEntity modelToEntity(UserInfo formInfo);

    List<UserInfo> entitiesToModels(List<UserEntity> formEntity);

    List<UserEntity> modelsToEntities(List<UserInfo> formInfo);
}
