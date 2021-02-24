package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.constant.RRoleMenuPrivilegeTypeEnum;
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
 * @description 角色
 * @date 2020-03-30 13:54
 */
@Service
@Transactional(rollbackFor = Exception.class)
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
        return true;
    }

    /**
     * 保存角色的菜单和权限数据
     *
     * @param roleInfo
     * @return
     */
    @Override
    public boolean saveRoleMenuPrivilege(RoleInfo roleInfo) {
        saveMenuPrivilege(roleInfo.getId(), roleInfo.getMenuInfos());
        return true;
    }

    /**
     * 保存角色的菜单和权限关系
     * @param roleId
     * @param menus 树结构
     * @return
     */
    private boolean saveMenuPrivilege(String roleId, List<MenuInfo> menus) {
        if (!StringUtils.isEmpty(roleId) && !CollectionUtils.isEmpty(menus)) {
            rRoleMenuPrivilegeService.deleteByRoleId(roleId);
            List<RRoleMenuPrivilegeEntity> rs = new ArrayList<>();
            getRByMenuTree(roleId, menus, rs);
            // 保存菜单和权限
            rRoleMenuPrivilegeRepository.saveAll(rs);
        }
        return true;
    }

    /**
     * 从树中找到r关系
     * @param menus
     * @param rs
     */
    private void getRByMenuTree(String roleId, List<MenuInfo> menus, List<RRoleMenuPrivilegeEntity> rs) {
        for(MenuInfo menuInfo : menus) {
            RRoleMenuPrivilegeEntity r = new RRoleMenuPrivilegeEntity();
            r.setId(UUID.randomUUID().toString());
            r.setType(RRoleMenuPrivilegeTypeEnum.ROLE_MENU.value());
            r.setRoleId(roleId);
            r.setMenuId(menuInfo.getId());
            r.setCreateTime(new Date());
            rs.add(r);
            menuInfo.getPrivilegeInfos().forEach(privilegeInfo -> {
                RRoleMenuPrivilegeEntity r2 = new RRoleMenuPrivilegeEntity();
                r2.setId(UUID.randomUUID().toString());
                r2.setType(RRoleMenuPrivilegeTypeEnum.ROLE_MENU_PRIVILEGE.value());
                r2.setRoleId(roleId);
                r2.setMenuId(menuInfo.getId());
                r2.setPrivilegeId(privilegeInfo.getId());
                r2.setCreateTime(new Date());
                rs.add(r2);
            });
            if (!CollectionUtils.isEmpty(menuInfo.getChildren())) {
                getRByMenuTree(roleId, menuInfo.getChildren(), rs);
            }
        }
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
     * @param hasMenuPrivilege 是否携带菜单权限数据
     * @return
     */
    @Override
    public List<RoleInfo> findList(RoleInfo roleInfo, boolean hasMenuPrivilege) {
        // 1、查询所有角色
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        List<RoleInfo> roleInfos = roleMapper.entitiesToModels(roleRepository.findAll(sort));
        if (hasMenuPrivilege) {
            // 携带上菜单权限数据
            rRoleMenuPrivilegeService.addMenuPrivilege(roleInfos);
        }
        return roleInfos;
    }

    /**
     * 查询启用的角色列表
     *
     * @param hasMenuPrivilege
     * @return
     */
    @Override
    public List<RoleInfo> findEnableList(boolean hasMenuPrivilege) {
        List<RoleInfo> roleInfos = roleMapper.entitiesToModels(roleRepository.findByEnableOrderByCreateTimeDesc(1));
        if (hasMenuPrivilege) {
            // 携带上菜单权限数据
            rRoleMenuPrivilegeService.addMenuPrivilege(roleInfos);
        }
        return roleInfos;
    }


    @Override
    public boolean disableRole(String id) {
        roleRepository.disableRole(id, 0);
        return true;
    }

    /**
     * 找到用户的角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<RoleInfo> getRoleInfosByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return new ArrayList<>();
        }
        return roleMapper.entitiesToModels(roleRepository.getRoleInfosByUserId(userId));
    }
}
