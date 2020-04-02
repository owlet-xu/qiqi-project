package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.constant.EnableEnum;
import com.qiqi.springboot.seed.bz1.contract.model.DepartmentInfo;
import com.qiqi.springboot.seed.bz1.contract.model.PageInfo;
import com.qiqi.springboot.seed.bz1.contract.model.UserInfo;
import com.qiqi.springboot.seed.bz1.contract.service.DepartmentService;
import com.qiqi.springboot.seed.bz1.service.datamappers.DepartmentMapper;
import com.qiqi.springboot.seed.bz1.service.datamappers.UserMapper;
import com.qiqi.springboot.seed.bz1.service.entity.DepartmentEntity;
import com.qiqi.springboot.seed.bz1.service.entity.RUserDepartmentRoleEntity;
import com.qiqi.springboot.seed.bz1.service.entity.UserEntity;
import com.qiqi.springboot.seed.bz1.service.entityfilter.UserEntityFilter;
import com.qiqi.springboot.seed.bz1.service.repository.DepartmentRepository;
import com.qiqi.springboot.seed.bz1.service.repository.RUserDepartmentRoleRepository;
import com.qiqi.springboot.seed.bz1.service.repository.UserRepository;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-23 09:20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    RUserDepartmentRoleRepository rUserDepartmentRoleRepository;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    /**
     * 保存部门
     *
     * @param departmentInfo
     * @return
     */
    @Override
    public Boolean saveDepartment(DepartmentInfo departmentInfo) {
        if (StringUtils.isEmpty(departmentInfo.getId())) {
            departmentInfo.setId(UUID.randomUUID().toString());
            departmentInfo.setCreateTime(new Date());
            departmentInfo.setEnable(1);
        }
        setDeepId(departmentInfo);
        departmentInfo.setUpdateTime(new Date());
        departmentRepository.saveAndFlush(departmentMapper.modelToEntity(departmentInfo));
        return true;
    }

    /**
     * 设置层级深度
     * @param departmentInfo
     */
    private void setDeepId(DepartmentInfo departmentInfo) {
        Integer deepId = 0;
        if (!StringUtils.isEmpty(departmentInfo.getParentId())){
            // 获取父的层级深度
            DepartmentEntity parent = departmentRepository.findById(departmentInfo.getParentId()).orElse(null);
            // 默认是0
            deepId = null != parent && null != parent.getDeepId() ? parent.getDeepId() + 1: 0;
        }
        departmentInfo.setDeepId(deepId);
    }

    /**
     * 分页查找数据
     *
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo<DepartmentInfo> findDepartmentListPage(PageInfo<DepartmentInfo> pageInfo) {
        Specification<DepartmentEntity> filters = createSpecification(pageInfo.getConditions());
        Sort sort = new Sort(Sort.Direction.DESC, "deepId");
        PageRequest pageRequest = PageRequest.of(pageInfo.getPage(), pageInfo.getSize());
        Page<DepartmentEntity> page2 = departmentRepository.findAll(filters, pageRequest);
        Page<DepartmentInfo> page = departmentRepository.findAll(filters, pageRequest).map(departmentMapper::entityToModel);
        pageInfo.setContents(page.getContent());
        pageInfo.setTotalCount(page.getTotalElements());
        pageInfo.setTotalPage(page.getTotalPages());
        return  pageInfo;
    }

    /**
     * 查询所有部门树
     *
     * @param departmentInfo
     * @return
     */
    @Override
    public List<DepartmentInfo> findDepartmentTree(DepartmentInfo departmentInfo) {
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        List<DepartmentInfo> list = departmentMapper.entitiesToModels(departmentRepository.findAll(sort));
        return buidTree(list);
    }

    /**
     * 删除部门，包括子部门
     *
     * @param id
     * @return
     */
    @Override
    public boolean disableDepartment(String id) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        List<DepartmentEntity> listAll = departmentRepository.findAll();
        List<String> childrenIds = new ArrayList<>();
        getChildrenIds(listAll, id, childrenIds);
        childrenIds.add(id);
        departmentRepository.disableDepartment(childrenIds, 0);
        return true;
    }

    /**
     * 获取部门人员,不包括禁用的人员
     *
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo<UserInfo> getDepartmentUsers(PageInfo<DepartmentInfo> pageInfo) {
        // 查出部门的人员Id
        List<String> userIds = rUserDepartmentRoleRepository.getUserIdsByDeptId(pageInfo.getConditions().getId());
        pageInfo.getConditions().setUserIds(userIds);
        Specification<UserEntity> filters = getUserCondition(pageInfo.getConditions(), pageInfo.getSearch(), 0);
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = PageRequest.of(pageInfo.getPage(), pageInfo.getSize(), sort);
        Page<UserInfo> page = userRepository.findAll(filters, pageRequest).map(entity -> {
            UserEntityFilter.getInstance().noPassword(entity);
            return userMapper.entityToModel(entity);
        });
        PageInfo<UserInfo> res = new PageInfo<>();
        res.setPage(pageInfo.getPage());
        res.setSize(pageInfo.getSize());
        res.setContents(page.getContent());
        res.setTotalCount(page.getTotalElements());
        res.setTotalPage(page.getTotalPages());
        return  res;
    }

    /**
     * 获取非本部门人员,不包括禁用的人员
     * 所以一个用户多个部门
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo<UserInfo> getOrderDepartmentUsers(PageInfo<DepartmentInfo> pageInfo) {
        // 查询出本部门的人员
        List<String> userIds = rUserDepartmentRoleRepository.getUserIdsByDeptId(pageInfo.getConditions().getId());
        pageInfo.getConditions().setUserIds(userIds);
        Specification<UserEntity> filters = getUserCondition(pageInfo.getConditions(), pageInfo.getSearch(), 1);
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = PageRequest.of(pageInfo.getPage(), pageInfo.getSize(), sort);
        Page<UserInfo> page = userRepository.findAll(filters, pageRequest).map(entity -> {
            UserEntityFilter.getInstance().noPassword(entity);
            return userMapper.entityToModel(entity);
        });
        PageInfo<UserInfo> res = new PageInfo<>();
        res.setPage(pageInfo.getPage());
        res.setSize(pageInfo.getSize());
        res.setContents(page.getContent());
        res.setTotalCount(page.getTotalElements());
        res.setTotalPage(page.getTotalPages());
        return  res;
    }

    /**
     * 查询部门的用户
     * @param departmentInfo
     * @param search
     * @param type 0 本部门用户，1 其他部门用户
     * @return
     */
    private Specification<UserEntity> getUserCondition(DepartmentInfo departmentInfo, String search, int type) {
        List<Predicate> predicatesAdvance = new ArrayList<>(); // 高接搜索，并列
        List<Predicate> predicatesCommon = new ArrayList<>(); // 模糊搜索，or
        Specification<UserEntity> filters = (root, query, criteriaBuilder) -> {
            // 没条件，返回
            if (null == departmentInfo && StringUtils.isEmpty(search)) {
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
            // 启用的用户
            Predicate enablePre = criteriaBuilder.equal(root.get("enable"), EnableEnum.ENABLE.value());
            predicatesAdvance.add(enablePre);
            if (type == 0 && departmentInfo.getUserIds().size() == 0) {
                // 查不到用户
                Predicate idsPre = criteriaBuilder.equal(root.get("id"), UUID.randomUUID().toString() );
                predicatesAdvance.add(idsPre);
            } else if (type == 0) {
                Predicate idsPre = root.get("id").in(departmentInfo.getUserIds());
                predicatesAdvance.add(idsPre);
            } else if(type == 1 && departmentInfo.getUserIds().size() > 0) {
                Predicate idsPre = criteriaBuilder.not(root.get("id").in(departmentInfo.getUserIds()));
                predicatesAdvance.add(idsPre);
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

    /**
     * 保存部门关联的人员信息
     *
     * @param departmentInfo
     * @return
     */
    @Override
    public boolean saveDepartmentUsers(DepartmentInfo departmentInfo) {
        if (null == departmentInfo) {
            return false;
        }
        if (StringUtils.isEmpty(departmentInfo.getId()) || CollectionUtils.isEmpty(departmentInfo.getUserIds())) {
            return false;
        }
        // 本部门已存在不用添加，其他部门可以添加，一个人对应多个部门
        List<String> userIds = rUserDepartmentRoleRepository.getUserIdsByDeptId(departmentInfo.getId());
        List<RUserDepartmentRoleEntity> list = new ArrayList<>();
        for(String userId : departmentInfo.getUserIds()) {
            if (userIds.contains(userId)) {
                continue;
            }
            RUserDepartmentRoleEntity entity = new RUserDepartmentRoleEntity();
            entity.setId(UUID.randomUUID().toString());
            entity.setDeptId(departmentInfo.getId());
            entity.setUserId(userId);
            entity.setType(0);
            entity.setCreateTime(new Date());
            list.add(entity);
        }
        rUserDepartmentRoleRepository.saveAll(list);
        return true;
    }

    /**
     * 保存部门关联的人员信息
     *
     * @param departmentInfos
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveDepartmentUsers(List<DepartmentInfo> departmentInfos) {
        for(DepartmentInfo departmentInfo:departmentInfos ) {
            this.saveDepartmentUsers(departmentInfo);
        }
        return true;
    }

    /**
     * 删除部门关联的人员信息
     *
     * @param departmentInfo
     * @return
     */
    @Override
    public boolean deleteDepartmentUsers(DepartmentInfo departmentInfo) {
        if (null == departmentInfo) {
            return false;
        }
        if (StringUtils.isEmpty(departmentInfo.getId()) || CollectionUtils.isEmpty(departmentInfo.getUserIds())) {
            return false;
        }
        return rUserDepartmentRoleRepository.deleteDepartmentUsers(departmentInfo.getId(), departmentInfo.getUserIds()) > -1;
    }

    /**
     * 获取树的子节点id
     * @param listAll 所有数据
     * @param parentId 父id
     * @param childrenIds 子id集合，返回的数据
     */
    private void getChildrenIds(List<DepartmentEntity> listAll,String parentId, List<String> childrenIds) {
        if (StringUtils.isEmpty(parentId) || CollectionUtils.isEmpty(listAll)) {
            return;
        }
        // 过滤出子
        List<DepartmentEntity> children = listAll.stream().filter(itemAll -> parentId.equals(itemAll.getParentId())).collect(Collectors.toList());
        // 循环子，获取id
        for(DepartmentEntity item : children) {
            childrenIds.add(item.getId());
            getChildrenIds(listAll, item.getId(),childrenIds );
        }
    }


    //把一个List转成树
    private List<DepartmentInfo> buidTree(List<DepartmentInfo> list){
        List<DepartmentInfo> tree = new ArrayList<>();
        for(DepartmentInfo node : list){
            if(StringUtils.isEmpty(node.getParentId())){
                tree.add(findChild(node,list));
            }
        }
        return tree;
    }

    private DepartmentInfo findChild(DepartmentInfo node, List<DepartmentInfo> list){
        for(DepartmentInfo n : list){
            if(node.getId().equals(n.getParentId())){
                if(null == node.getChildren()){
                    node.setChildren(new ArrayList<DepartmentInfo>());
                }
                node.getChildren().add(findChild(n,list));
            }
        }
        return node;
    }

    /**
     * @param departmentInfo
     * @return
     */
    private Specification<DepartmentEntity> createSpecification(DepartmentInfo departmentInfo) {
        List<Predicate> predicatesAdvance = new ArrayList<>(); // 高接搜索，并列
        Specification<DepartmentEntity> filters = (root, query, criteriaBuilder) -> {
            // 没条件，返回
            if (null == departmentInfo) {
                return null;
            }
            if (!StringUtils.isEmpty(departmentInfo.getName())) {
                Predicate namePre = criteriaBuilder.like(root.get("name"),  RepositoryUtils.prefixForLike(departmentInfo.getName()));
                predicatesAdvance.add(namePre);
            }
            // 默认查询启用的部门
            if (null != departmentInfo.getEnable() && departmentInfo.getEnable() != EnableEnum.ALL.value()) {
                Predicate enablePre = criteriaBuilder.equal(root.get("enable"), departmentInfo.getEnable());
                predicatesAdvance.add(enablePre);
            }
            return criteriaBuilder.and(predicatesAdvance.toArray(new Predicate[predicatesAdvance.size()]));
        };
        return filters;
    }
}
