package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.model.MenuInfo;
import com.qiqi.springboot.seed.bz1.contract.model.RoleInfo;
import com.qiqi.springboot.seed.bz1.contract.service.RRoleMenuPrivilegeService;
import com.qiqi.springboot.seed.bz1.contract.service.RoleService;
import com.qiqi.springboot.seed.bz1.service.datamappers.MenuMapper;
import com.qiqi.springboot.seed.bz1.service.datamappers.PrivilegeMapper;
import com.qiqi.springboot.seed.bz1.service.datamappers.RoleMapper;
import com.qiqi.springboot.seed.bz1.service.entity.RRoleMenuPrivilegeEntity;
import com.qiqi.springboot.seed.bz1.service.repository.MenuRepository;
import com.qiqi.springboot.seed.bz1.service.repository.PrivilegeRepository;
import com.qiqi.springboot.seed.bz1.service.repository.RRoleMenuPrivilegeRepository;
import com.qiqi.springboot.seed.bz1.service.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 13:54
 */
@Service
public class RoleServiceImpl implements RoleService {
    // repository
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RRoleMenuPrivilegeRepository rRoleMenuPrivilegeRepository;
    @Autowired
    PrivilegeRepository privilegeRepository;
    @Autowired
    MenuRepository menuRepository;
    // mappers
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PrivilegeMapper privilegeMapper;
    @Autowired
    MenuMapper menuMapper;
    // services
    @Autowired
    RRoleMenuPrivilegeService rRoleMenuPrivilegeService;

    /**
     * 保存角色
     *
     * @param roleInfo
     * @return
     */
    @Override
    public boolean save(RoleInfo roleInfo) {
        if (StringUtils.isEmpty(roleInfo.getId())) {
            roleInfo.setId(UUID.randomUUID().toString());
            roleInfo.setCreateTime(new Date());
        }
        roleInfo.setUpdateTime(new Date());
        roleRepository.saveAndFlush(roleMapper.modelToEntity(roleInfo));
        // 保存角色的菜单和权限
        return saveMenuPrivilege(roleInfo.getId(), roleInfo.getMenuInfos());
    }

    /**
     * 保存角色的菜单和权限关系
     * @param roleId
     * @param menus
     * @return
     */
    private boolean saveMenuPrivilege(String roleId, List<MenuInfo> menus) {
        if (!CollectionUtils.isEmpty(menus)) {
            rRoleMenuPrivilegeService.deleteByRoleId(roleId);
            List<RRoleMenuPrivilegeEntity> rs = new ArrayList<>();
            menus.forEach(menuInfo -> {
                RRoleMenuPrivilegeEntity r = new RRoleMenuPrivilegeEntity();
                r.setId(UUID.randomUUID().toString());
                r.setType(0);
                r.setRoleId(roleId);
                r.setMenuId(menuInfo.getId());
                r.setCreateTime(new Date());
                rs.add(r);
                menuInfo.getPrivilegeInfos().forEach(privilegeInfo -> {
                    RRoleMenuPrivilegeEntity r2 = new RRoleMenuPrivilegeEntity();
                    r2.setType(1);
                    r2.setMenuId(menuInfo.getId());
                    r2.setPrivilegeId(privilegeInfo.getId());
                    r2.setCreateTime(new Date());
                    rs.add(r2);
                });
            });
            // 保存菜单和权限
            rRoleMenuPrivilegeRepository.saveAll(rs);
        }
        return true;
    }

    /**
     * 禁用角色
     *
     * @param id
     * @return
     */
    @Override
    public boolean enableRole(String id) {
        return false;
    }

    /**
     * 给某个用户添加角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
    @Override
    public boolean addUserRoles(String userId, List<String> roleIds) {
        return false;
    }

    /**
     * 条件查找所有角色列表
     *
     * @param roleInfo
     * @return
     */
    @Override
    public List<RoleInfo> findList(RoleInfo roleInfo) {
        // 1、查询所有角色
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        List<RoleInfo> roleInfos = roleMapper.entitiesToModels(roleRepository.findAll(sort));
        // 携带上菜单权限数据
        rRoleMenuPrivilegeService.addMenuPrivilege(roleInfos);
        return roleInfos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disableRole(String id) {
        roleRepository.disableRole(id, 0);
        return true;
    }
}
