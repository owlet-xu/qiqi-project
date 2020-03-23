package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.constant.EnableEnum;
import com.qiqi.springboot.seed.bz1.contract.model.DepartmentInfo;
import com.qiqi.springboot.seed.bz1.contract.model.PageInfo;
import com.qiqi.springboot.seed.bz1.contract.service.DepartmentService;
import com.qiqi.springboot.seed.bz1.service.datamappers.DepartmentMapper;
import com.qiqi.springboot.seed.bz1.service.entity.DepartmentEntity;
import com.qiqi.springboot.seed.bz1.service.repository.DepartmentRepository;
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
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentMapper departmentMapper;

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
        departmentInfo.setUpdateTime(new Date());
        departmentRepository.saveAndFlush(departmentMapper.modelToEntity(departmentInfo));
        return true;
    }

    /**
     * 分页查找数据
     *
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo<DepartmentInfo> findDepartmentListPage(PageInfo<DepartmentInfo> pageInfo) {
        Specification<DepartmentEntity> filters = createSpecification(pageInfo.getConditions(), pageInfo.getSearch());
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = PageRequest.of(pageInfo.getPage(), pageInfo.getSize(), sort);
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
    @Transactional
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
     * 获取树的子节点
     * @param listAll 所有数据
     * @param parentId 父id
     * @param childrenIds 子id集合
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

    private Specification<DepartmentEntity> createSpecification(DepartmentInfo departmentInfo, String search) {
        List<Predicate> predicatesAdvance = new ArrayList<>(); // 高接搜索，并列
        List<Predicate> predicatesCommon = new ArrayList<>(); // 模糊搜索，or
        Specification<DepartmentEntity> filters = (root, query, criteriaBuilder) -> {
            // 没条件，返回
            if (null == departmentInfo && StringUtils.isEmpty(search)) {
                return null;
            }
            // 综合搜搜
            if (!StringUtils.isEmpty(search)) {
                Predicate namePre = criteriaBuilder.like(root.get("name"), RepositoryUtils.prefixForLike(search));
                predicatesCommon.add(namePre);
            }
            if (!StringUtils.isEmpty(departmentInfo.getName())) {
                Predicate namePre = criteriaBuilder.like(root.get("name"),  RepositoryUtils.prefixForLike(departmentInfo.getName()));
                predicatesAdvance.add(namePre);
            }
            // 默认查询启用的用户
            if (null != departmentInfo.getEnable() && departmentInfo.getEnable() != EnableEnum.ALL.value()) {
                Predicate enablePre = criteriaBuilder.equal(root.get("enable"), departmentInfo.getEnable());
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
