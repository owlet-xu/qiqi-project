package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.constant.EnableEnum;
import com.qiqi.springboot.seed.bz1.contract.constant.UserTypeEnum;
import com.qiqi.springboot.seed.bz1.contract.model.DepartmentInfo;
import com.qiqi.springboot.seed.bz1.contract.model.PageInfo;
import com.qiqi.springboot.seed.bz1.contract.model.UserInfo;
import com.qiqi.springboot.seed.bz1.contract.service.DepartmentService;
import com.qiqi.springboot.seed.bz1.contract.service.UserService;
import com.qiqi.springboot.seed.bz1.service.datamappers.DepartmentMapper;
import com.qiqi.springboot.seed.bz1.service.datamappers.UserMapper;
import com.qiqi.springboot.seed.bz1.service.entity.DepartmentEntity;
import com.qiqi.springboot.seed.bz1.service.entity.RUserDepartmentRoleEntity;
import com.qiqi.springboot.seed.bz1.service.entity.UserEntity;
import com.qiqi.springboot.seed.bz1.service.entityfilter.UserEntityFilter;
import com.qiqi.springboot.seed.bz1.service.repository.DepartmentRepository;
import com.qiqi.springboot.seed.bz1.service.repository.RUserDepartmentRoleRepository;
import com.qiqi.springboot.seed.bz1.service.repository.UserRepository;
import com.qiqi.springboot.seed.common.configs.XseedSettings;
import com.qiqi.springboot.seed.common.exception.BusinessException;
import com.qiqi.springboot.seed.common.exception.ResultStatus;
import com.qiqi.springboot.seed.common.util.RepositoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    RUserDepartmentRoleRepository rUserDepartmentRoleRepository;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    DepartmentService departmentService;

    @Override
    public PageInfo<UserInfo> findUserListPage(PageInfo<UserInfo> pageInfo) {
        // 准备部门数据
        Map<String, List<DepartmentInfo>> userIdDeptMap = getUserDepartment();
        // 查询用户
        Specification<UserEntity> filters = createSpecification(pageInfo.getConditions(), pageInfo.getSearch());
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = PageRequest.of(pageInfo.getPage(), pageInfo.getSize(), sort);
        Page<UserInfo> page = userRepository.findAll(filters, pageRequest).map(entity -> {
            UserEntityFilter.getInstance().noPassword(entity);
            UserInfo userInfo = userMapper.entityToModel(entity);
            userInfo.setDeptInfos(userIdDeptMap.get(userInfo.getId()));
            return userInfo;

        });
        pageInfo.setContents(page.getContent());
        pageInfo.setTotalCount(page.getTotalElements());
        pageInfo.setTotalPage(page.getTotalPages());
        return  pageInfo;
    }


    /**
     * 构造出userId -> List<DepartmentEntity> 结构
     * @return
     */
    private Map<String, List<DepartmentInfo>> getUserDepartment() {
        // 构造出用户对应的部门数据
        Map<String, List<DepartmentInfo>> userDepartments = new HashMap<>();
        // 取出所有部门数据，转换成 id ->  list 结构
        Map<String, List<DepartmentEntity>> departmentsMap = departmentRepository.findAll().stream().collect(Collectors.groupingBy(DepartmentEntity::getId));
        // 取出所有部门用户关系， 转换成 userid ->  list 结构
        Map<String, List<RUserDepartmentRoleEntity>> rUserDeparmentsMap = rUserDepartmentRoleRepository.findByType(0).stream().collect(Collectors.groupingBy(RUserDepartmentRoleEntity::getUserId));
        // 部门数据，或者关系数据为空返回空对象
        if (departmentsMap.keySet().size() == 0 || rUserDeparmentsMap.keySet().size() == 0) {
            return userDepartments;
        }
        // 设置结构数据
        for(String userId : rUserDeparmentsMap.keySet()) {
            List<DepartmentEntity> listD = new ArrayList<>();
            for(RUserDepartmentRoleEntity entity : rUserDeparmentsMap.get(userId)) {
                listD.add(departmentsMap.get(entity.getDeptId()).get(0));
            }
            // 按照deepId 排序
            listD = listD.stream().sorted(Comparator.comparing(DepartmentEntity::getDeepId)).collect(Collectors.toList());
            userDepartments.put(userId, departmentMapper.entitiesToModels(listD));
        }
        return userDepartments;
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
        // 更新用户部门信息
        if (null != userInfo.getDeptInfos()) {
            // 删除用户部门信息
            rUserDepartmentRoleRepository.deleteDepartmentByUserId(userEntity.getId());
            // 添加用户部门信息
            for (DepartmentInfo departmentInfo : userInfo.getDeptInfos()) {
               List<String> userIds = new ArrayList<>();
               userIds.add(userEntity.getId());
               departmentInfo.setUserIds(userIds);
            }
            departmentService.saveDepartmentUsers(userInfo.getDeptInfos());
        }
        return true;
    }

    private UserEntity add(UserInfo userInfo) {
        UserEntity userEntity = userMapper.modelToEntity(userInfo);
        userEntity.setId(UUID.randomUUID().toString());
        userEntity.setEnable(1);
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
        userEntity.setHeadImg(userInfo.getHeadImg());
        userEntity.setUserIntegral( userInfo.getUserIntegral() );
        userEntity.setUserLevel( userInfo.getUserLevel() );
        userEntity.setUpdateTime(new Date());
        return userEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean disableUser(String id) {
        userRepository.disableUser(id, EnableEnum.DISABLED.value());
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
                Predicate namePre = criteriaBuilder.like(root.get("name"), RepositoryUtils.prefixForLike(search));
                predicatesCommon.add(namePre);
                Predicate userNamePre = criteriaBuilder.like(root.get("userName"), RepositoryUtils.prefixForLike(search));
                predicatesCommon.add(userNamePre);
                Predicate mobilePre = criteriaBuilder.like(root.get("mobile"), RepositoryUtils.prefixForLike(search));
                predicatesCommon.add(mobilePre);
                Predicate emailPre = criteriaBuilder.like(root.get("email"), RepositoryUtils.prefixForLike(search));
                predicatesCommon.add(emailPre);
            }
            if (!StringUtils.isEmpty(userInfo.getName())) {
                Predicate namePre = criteriaBuilder.like(root.get("name"),  RepositoryUtils.prefixForLike(userInfo.getName()));
                predicatesAdvance.add(namePre);
            }
            if (!StringUtils.isEmpty(userInfo.getUserName())) {
                Predicate namePre = criteriaBuilder.like(root.get("userName"), RepositoryUtils.prefixForLike(userInfo.getUserName()));
                predicatesAdvance.add(namePre);
            }
            if (!StringUtils.isEmpty(userInfo.getMobile())) {
                Predicate namePre = criteriaBuilder.like(root.get("mobile"), RepositoryUtils.prefixForLike(userInfo.getMobile()));
                predicatesAdvance.add(namePre);
            }
            if (!StringUtils.isEmpty(userInfo.getEmail())) {
                Predicate namePre = criteriaBuilder.like(root.get("email"), RepositoryUtils.prefixForLike(userInfo.getEmail()));
                predicatesAdvance.add(namePre);
            }
            // 默认查询启用的用户
            if (null != userInfo.getEnable() && userInfo.getEnable() != EnableEnum.ALL.value()) {
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
}
