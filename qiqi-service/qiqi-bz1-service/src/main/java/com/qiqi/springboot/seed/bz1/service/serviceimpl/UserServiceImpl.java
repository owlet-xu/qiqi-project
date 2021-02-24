package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.constant.EnableEnum;
import com.qiqi.springboot.seed.bz1.contract.constant.RUserDepartmentRoleTypeEnum;
import com.qiqi.springboot.seed.bz1.contract.constant.UserTypeEnum;
import com.qiqi.springboot.seed.bz1.contract.model.DepartmentInfo;
import com.qiqi.springboot.seed.bz1.contract.model.PageInfo;
import com.qiqi.springboot.seed.bz1.contract.model.RoleInfo;
import com.qiqi.springboot.seed.bz1.contract.model.UserInfo;
import com.qiqi.springboot.seed.bz1.contract.service.DepartmentService;
import com.qiqi.springboot.seed.bz1.contract.service.RoleService;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xgy
 * @description 用户
 * @date 2018/9/6
 */
@Service
@Transactional(rollbackFor = Exception.class)
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

    // services
    @Autowired
    DepartmentService departmentService;
    @Autowired
    RoleService roleService;

    @Override
    public PageInfo<UserInfo> findUserListPage(PageInfo<UserInfo> pageInfo) {
        // 准备部门数据
        Map<String, List<DepartmentInfo>> userIdDeptMap = getUserDepartments();
        // 准备角色数据
        Map<String, List<RoleInfo>> userIdRoleMap = getUserRoles();
        // 查询用户
        Specification<UserEntity> filters = createSpecification(pageInfo.getConditions(), pageInfo.getSearch());
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = PageRequest.of(pageInfo.getPage(), pageInfo.getSize(), sort);
        Page<UserInfo> page = userRepository.findAll(filters, pageRequest).map(entity -> {
            UserInfo userInfo = userMapper.entityToModel(entity);
            UserEntityFilter.getInstance().noPassword(userInfo);
            userInfo.setDeptInfos(userIdDeptMap.get(userInfo.getId()));
            userInfo.setRoleInfos(userIdRoleMap.get(userInfo.getId()));
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
    private Map<String, List<DepartmentInfo>> getUserDepartments() {
        // 构造出用户对应的部门数据
        Map<String, List<DepartmentInfo>> userDepartments = new HashMap<>();
        // 取出所有部门数据，转换成 id ->  list 结构
        Map<String, List<DepartmentEntity>> departmentsMap = departmentRepository.findByEnable(1).stream().collect(Collectors.groupingBy(DepartmentEntity::getId));
        // 取出所有部门用户关系， 转换成 userid ->  list 结构
        Map<String, List<RUserDepartmentRoleEntity>> rUserDeparmentsMap = rUserDepartmentRoleRepository.findByType(RUserDepartmentRoleTypeEnum.DEPARTMENT.value()).stream().collect(Collectors.groupingBy(RUserDepartmentRoleEntity::getUserId));
        // 部门数据，或者关系数据为空返回空对象
        if (departmentsMap.keySet().size() == 0 || rUserDeparmentsMap.keySet().size() == 0) {
            return userDepartments;
        }
        // 设置结构数据
        for(String userId : rUserDeparmentsMap.keySet()) {
            List<DepartmentEntity> listD = new ArrayList<>();
            for(RUserDepartmentRoleEntity entity : rUserDeparmentsMap.get(userId)) {
                List<DepartmentEntity> depts = departmentsMap.get(entity.getDeptId());
                if (!CollectionUtils.isEmpty(depts)) {
                    listD.add(depts.get(0));
                }
            }
            // 按照deepId 排序
            listD = listD.stream().sorted(Comparator.comparing(DepartmentEntity::getDeepId)).collect(Collectors.toList());
            userDepartments.put(userId, departmentMapper.entitiesToModels(listD));
        }
        return userDepartments;
    }

    /**
     * 构造出userId -> List<RoleInfo> 结构
     * @return
     */
    private Map<String, List<RoleInfo>> getUserRoles() {
        // 构造出用户对应的角色数据
        Map<String, List<RoleInfo>> userRoles = new HashMap<>();
        // 1、取出所有启用的角色数据，转换成 id ->  list 结构
        Map<String, List<RoleInfo>> rolesMap = roleService.findEnableList(false).stream().collect(Collectors.groupingBy(RoleInfo::getId));
        // 2、取出所有角色用户关系， 转换成 userid ->  list 结构
        Map<String, List<RUserDepartmentRoleEntity>> rUserRolesMap = rUserDepartmentRoleRepository.findByType(RUserDepartmentRoleTypeEnum.ROLE.value()).stream().collect(Collectors.groupingBy(RUserDepartmentRoleEntity::getUserId));
        // 3、角色数据，或者关系数据为空返回空对象
        if (rUserRolesMap.keySet().size() == 0 || rUserRolesMap.keySet().size() == 0) {
            return userRoles;
        }
        // 设置结构数据
        for(String userId : rUserRolesMap.keySet()) {
            List<RoleInfo> listR = new ArrayList<>();
            for(RUserDepartmentRoleEntity entity : rUserRolesMap.get(userId)) {
                List<RoleInfo> roles = rolesMap.get(entity.getRoleId());
                if (!CollectionUtils.isEmpty(roles)) {
                    listR.add(roles.get(0));
                }
            }
            userRoles.put(userId, listR);
        }
        return userRoles;
    }


    @Override
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
        saveUserDeptsRoles(userInfo.getDeptInfos(),userInfo.getRoleInfos(), userEntity.getId());
        return true;
    }

    /**
     * 保存用户的部门和角色关系
     * @param depts
     * @param roles
     * @param userId
     */
    private void saveUserDeptsRoles(List<DepartmentInfo> depts, List<RoleInfo> roles, String userId) {
        // 用户的部门和角色关系表
        List<RUserDepartmentRoleEntity> rDR = new ArrayList<>();
        // 更新用户部门信息
        if (null != depts) {
            // 删除用户部门信息
            rUserDepartmentRoleRepository.deleteDepartmentByUserId(userId);
            for (DepartmentInfo departmentInfo : depts) {
                RUserDepartmentRoleEntity rDept = new RUserDepartmentRoleEntity();
                rDept.setId(UUID.randomUUID().toString());
                rDept.setType(RUserDepartmentRoleTypeEnum.DEPARTMENT.value());
                rDept.setUserId(userId);
                rDept.setDeptId(departmentInfo.getId());
                rDept.setCreateTime(new Date());
                rDR.add(rDept);

            }
        }
        // 更新用户的角色信息
        if (null != roles) {
            // 删除用户角色信息
            rUserDepartmentRoleRepository.deleteRoleByUserId(userId);
            // 添加用户角色信息

            for (RoleInfo roleInfo : roles) {
                RUserDepartmentRoleEntity rRole = new RUserDepartmentRoleEntity();
                rRole.setId(UUID.randomUUID().toString());
                rRole.setType(RUserDepartmentRoleTypeEnum.ROLE.value());
                rRole.setUserId(userId);
                rRole.setRoleId(roleInfo.getId());
                rRole.setCreateTime(new Date());
                rDR.add(rRole);
            }
        }
        if (!CollectionUtils.isEmpty(rDR)) {
            rUserDepartmentRoleRepository.saveAll(rDR);
        }
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

    /**
     * 给用户加上角色信息
     *
     * @param userInfo
     * @return
     */
    @Override
    public UserInfo addRoleInfo(UserInfo userInfo) {
        if (null == userInfo || StringUtils.isEmpty(userInfo.getId())) {
            return userInfo;
        }
        userInfo.setRoleInfos(roleService.getRoleInfosByUserId(userInfo.getId()));
        return userInfo;
    }

    @Override
    public List<String> getUserRoleIds(String userId) {
        return rUserDepartmentRoleRepository.getRoleIdsByUserId(userId);
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
