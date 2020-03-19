package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.constant.UserEnableEnum;
import com.qiqi.springboot.seed.bz1.contract.constant.UserTypeEnum;
import com.qiqi.springboot.seed.bz1.contract.model.PageInfo;
import com.qiqi.springboot.seed.bz1.contract.model.UserInfo;
import com.qiqi.springboot.seed.bz1.contract.service.UserService;
import com.qiqi.springboot.seed.bz1.service.datamappers.UserMapper;
import com.qiqi.springboot.seed.bz1.service.entity.UserEntity;
import com.qiqi.springboot.seed.bz1.service.entityfilter.UserEntityFilter;
import com.qiqi.springboot.seed.bz1.service.repository.UserRepository;
import com.qiqi.springboot.seed.common.configs.XseedSettings;
import com.qiqi.springboot.seed.common.exception.BusinessException;
import com.qiqi.springboot.seed.common.exception.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author xgy
 * @description
 * @date 2018/9/6
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public PageInfo<UserInfo> findUserListPage(PageInfo<UserInfo> pageInfo) {
        Specification<UserEntity> filters = createSpecification(pageInfo.getConditions(), pageInfo.getSearch());
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = PageRequest.of(pageInfo.getPage(), pageInfo.getSize(), sort);
        Page<UserInfo> page = userRepository.findAll(filters, pageRequest).map(entity -> {
            UserEntityFilter.getInstance().noPassword(entity);
            return userMapper.entityToModel(entity);
        });
        pageInfo.setContents(page.getContent());
        pageInfo.setTotalCount(page.getTotalElements());
        pageInfo.setTotalPage(page.getTotalPages());
        return  pageInfo;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUser(UserInfo userInfo) {
        // 超级管理员
        if (StringUtils.isEmpty(userInfo.getUserType()) || UserTypeEnum.ADMIN.value().equals(userInfo.getUserType())) {
            throw new BusinessException(ResultStatus.PARAM_TYPE_ERROR);
        }
        UserEntity userEntity;
        if (StringUtils.isEmpty(userInfo.getId())) {
            userEntity = add(userInfo);
        } else {
            userEntity = update(userInfo);
        }
        userRepository.saveAndFlush(userEntity);
        return true;
    }

    private UserEntity add(UserInfo userInfo) {
        UserEntity userEntity = userMapper.modelToEntity(userInfo);
        userEntity.setId(UUID.randomUUID().toString());
        userEntity.setUserType(UserTypeEnum.COMMON.value());
        userEntity.setPassword(XseedSettings.defaultPassword);
        userEntity.setCreateTime(new Date());
        userEntity.setUpdateTime(new Date());
        return userEntity;
    }

    private UserEntity update(UserInfo userInfo) {
        // 更新
        UserEntity userEntity = userRepository.findById(userInfo.getId()).orElseGet(null);
        if (null == userEntity) {
            throw new BusinessException(ResultStatus.DATA_NOT_EXIST);
        }
        userEntity.setName( userInfo.getName() );
        userEntity.setUserName( userInfo.getUserName() );
        userEntity.setMobile( userInfo.getMobile() );
        userEntity.setEmail( userInfo.getEmail() );
        userEntity.setUserType( userInfo.getUserType() );
        userEntity.setEnable( userInfo.getEnable() );
        userEntity.setUserIntegral( userInfo.getUserIntegral() );
        userEntity.setUserLevel( userInfo.getUserLevel() );
        userEntity.setUpdateTime(new Date());
        return userEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean disableUser(String id) {
        userRepository.disableUser(id, UserEnableEnum.DISABLED.value());
        return true;
    }

    private Specification<UserEntity> createSpecification(UserInfo userInfo, String search) {
        List<Predicate> predicatesAdvance = new ArrayList<>(); // 高接搜索，并列
        List<Predicate> predicatesCommon = new ArrayList<>(); // 模糊搜索，or
        Specification<UserEntity> filters = (root, query, criteriaBuilder) -> {
            // 没条件，返回
            if (null == userInfo && StringUtils.isEmpty(search)) {
                return null;
            }
            // 综合搜搜
            if (!StringUtils.isEmpty(search)) {
                Predicate namePre = criteriaBuilder.like(root.get("name"), prefixForLike(search));
                predicatesCommon.add(namePre);
                Predicate userNamePre = criteriaBuilder.like(root.get("userName"), prefixForLike(search));
                predicatesCommon.add(userNamePre);
                Predicate mobilePre = criteriaBuilder.like(root.get("mobile"), prefixForLike(search));
                predicatesCommon.add(mobilePre);
                Predicate emailPre = criteriaBuilder.like(root.get("email"), prefixForLike(search));
                predicatesCommon.add(emailPre);
            }
            if (!StringUtils.isEmpty(userInfo.getName())) {
                Predicate namePre = criteriaBuilder.like(root.get("name"),  prefixForLike(userInfo.getName()));
                predicatesAdvance.add(namePre);
            }
            if (!StringUtils.isEmpty(userInfo.getUserName())) {
                Predicate namePre = criteriaBuilder.like(root.get("userName"), prefixForLike(userInfo.getUserName()));
                predicatesAdvance.add(namePre);
            }
            if (!StringUtils.isEmpty(userInfo.getMobile())) {
                Predicate namePre = criteriaBuilder.like(root.get("mobile"), prefixForLike(userInfo.getMobile()));
                predicatesAdvance.add(namePre);
            }
            if (!StringUtils.isEmpty(userInfo.getEmail())) {
                Predicate namePre = criteriaBuilder.like(root.get("email"), prefixForLike(userInfo.getEmail()));
                predicatesAdvance.add(namePre);
            }
            // 默认查询启用的用户
            if (null != userInfo.getEnable() && userInfo.getEnable() != UserEnableEnum.ALL.value()) {
                Predicate enablePre = criteriaBuilder.equal(root.get("enable"), userInfo.getEnable());
                predicatesAdvance.add(enablePre);
            }
            Predicate predicateCommon = criteriaBuilder.or(predicatesCommon.toArray(new Predicate[predicatesCommon.size()]));
            Predicate predicateAdvance = criteriaBuilder.and(predicatesAdvance.toArray(new Predicate[predicatesAdvance.size()]));



            if (predicatesCommon.size() > 0 && predicatesAdvance.size() > 0) {
                Predicate[] predicate = { predicateCommon, predicateAdvance };
                return criteriaBuilder.and(predicate);
            } else if (predicatesCommon.size() > 0) {
                return predicateCommon;
            } else if (predicatesAdvance.size() > 0) {
                return predicateAdvance;
            } else {
                return null;
            }
        };
        return filters;
    }

    private String prefixForLike(String val) {
        return "%" + val+ "%";
    }
}
