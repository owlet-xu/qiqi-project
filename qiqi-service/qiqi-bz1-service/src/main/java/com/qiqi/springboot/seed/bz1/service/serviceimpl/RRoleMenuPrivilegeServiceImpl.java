package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.constant.RRoleMenuPrivilegeTypeEnum;
import com.qiqi.springboot.seed.bz1.contract.model.MenuInfo;
import com.qiqi.springboot.seed.bz1.contract.model.PrivilegeInfo;
import com.qiqi.springboot.seed.bz1.contract.model.RoleInfo;
import com.qiqi.springboot.seed.bz1.contract.service.RRoleMenuPrivilegeService;
import com.qiqi.springboot.seed.bz1.service.datamappers.MenuMapper;
import com.qiqi.springboot.seed.bz1.service.datamappers.PrivilegeMapper;
import com.qiqi.springboot.seed.bz1.service.datamappers.RoleMapper;
import com.qiqi.springboot.seed.bz1.service.entity.RRoleMenuPrivilegeEntity;
import com.qiqi.springboot.seed.bz1.service.repository.MenuRepository;
import com.qiqi.springboot.seed.bz1.service.repository.PrivilegeRepository;
import com.qiqi.springboot.seed.bz1.service.repository.RRoleMenuPrivilegeRepository;
import com.qiqi.springboot.seed.bz1.service.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 15:33
 */
@Service
public class RRoleMenuPrivilegeServiceImpl implements RRoleMenuPrivilegeService {

    @Autowired
    RRoleMenuPrivilegeRepository rRoleMenuPrivilegeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Autowired
    PrivilegeMapper privilegeMapper;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    MenuMapper menuMapper;

    /**
     * 查询启用菜单和权限树
     *
     * @return
     */
    @Override
    public List<MenuInfo> findMenuPrivelegeTree() {
        List<MenuInfo> menuInfos = menuMapper.entitiesToModels(menuRepository.findByEnable(1));
        addPrivileges(menuInfos);
        return buidTree(menuInfos);
    }

    /**
     * 查询所有菜单和权限树
     *
     * @return
     */
    @Override
    public List<MenuInfo> findAllMenuPrivelegeTree() {
        List<MenuInfo> menuInfos = menuMapper.entitiesToModels(menuRepository.findAll());
        addPrivileges(menuInfos);
        return buidTree(menuInfos);
    }

    private List<MenuInfo>  addPrivileges(List<MenuInfo> menuInfos) {
        // 1、菜单的id
        List<String> menuIds = menuInfos.stream().map(MenuInfo::getId).collect(Collectors.toList());
        // 2、查询菜单下权限关系和实体
        List<RRoleMenuPrivilegeEntity> rAll = rRoleMenuPrivilegeRepository.findByTypeAndMenuIdIn(RRoleMenuPrivilegeTypeEnum.MENU_PRIVILEGE.value(), menuIds);
        List<String> pIds = rAll.stream().map(RRoleMenuPrivilegeEntity::getPrivilegeId).collect(Collectors.toList());
        List<PrivilegeInfo> privilegeAll = privilegeMapper.entitiesToModels(privilegeRepository.findByIdIn(pIds));
        // 3、给每个菜单设置权限
        for(MenuInfo menu : menuInfos) {
            List<PrivilegeInfo> privilegeInfos = getPrivilegeInfosByMenuId(menu.getId(), rAll, privilegeAll);
            menu.setPrivilegeInfos(privilegeInfos);
        }
        return menuInfos;
    }

    /**
     * 通过角色Id返回角色的菜单权限树
     *
     * @param roleIds
     * @return
     */
    @Override
    public List<MenuInfo> findRoleMenuPrivelegeTree(List<String> roleIds) {
        List<MenuInfo> menuInfos = findRoleMenuPrivelegeList(roleIds);
        // 菜单变成树结构
        return buidTree(menuInfos);
    }

    /**
     * 通过角色Id返回角色的菜单权限列表
     *
     * @param roleIds
     * @return
     */
    @Override
    public List<MenuInfo> findRoleMenuPrivelegeList(List<String> roleIds) {
        // 1、查询角色的菜单实体
        List<MenuInfo> menuInfos = menuMapper.entitiesToModels(menuRepository.getMenusByRoleIds(roleIds));
        List<String> menuIds = menuInfos.stream().map(MenuInfo::getId).collect(Collectors.toList());
        // 2、查询菜单下权限关系和实体
        List<RRoleMenuPrivilegeEntity> rAll = rRoleMenuPrivilegeRepository.findByTypeAndMenuIdIn(RRoleMenuPrivilegeTypeEnum.MENU_PRIVILEGE.value(), menuIds);
        List<String> pIds = rAll.stream().map(RRoleMenuPrivilegeEntity::getPrivilegeId).collect(Collectors.toList());
        List<PrivilegeInfo> privilegeAll = privilegeMapper.entitiesToModels(privilegeRepository.findByIdIn(pIds));
        // 3、给每个菜单设置权限
        for(MenuInfo menu : menuInfos) {
            List<PrivilegeInfo> privilegeInfos = getPrivilegeInfosByMenuId(menu.getId(), rAll, privilegeAll);
            menu.setPrivilegeInfos(privilegeInfos);
        }
        return menuInfos;
    }


    //把一个List转成树
    private List<MenuInfo> buidTree(List<MenuInfo> list){
        List<MenuInfo> tree = new ArrayList<>();
        for(MenuInfo node : list){
            if(StringUtils.isEmpty(node.getParentId())){
                tree.add(findChild(node,list));
            }
        }
        return tree;
    }

    private MenuInfo findChild(MenuInfo node, List<MenuInfo> list){
        for(MenuInfo n : list){
            if(node.getId().equals(n.getParentId())){
                if(null == node.getChildren()){
                    node.setChildren(new ArrayList<MenuInfo>());
                }
                node.getChildren().add(findChild(n,list));
            }
        }
        return node;
    }



    /**
     * 给加上菜单和权限
     *
     * @param roleInfos
     * @return
     */
    @Override
    public List<RoleInfo> addMenuPrivilege(List<RoleInfo> roleInfos) {
        // 1、查询所有关系表
        List<String> roleIds = roleInfos.stream().map(RoleInfo::getId).collect(Collectors.toList());
        List<RRoleMenuPrivilegeEntity> rAll = rRoleMenuPrivilegeRepository.findByRoleIdIn(roleIds);
        // 2、插叙所有菜单和权限表
        List<MenuInfo> menuAll = menuMapper.entitiesToModels(menuRepository.findAll());
        List<PrivilegeInfo> privilegeAll = privilegeMapper.entitiesToModels(privilegeRepository.findAll());
        // 3、循环角色加上菜单和权限
        for (RoleInfo roleInfo : roleInfos) {
            addMenuPrivilege(roleInfo, menuAll, privilegeAll, rAll);
        }
        return roleInfos;
    }

    /**
     * 删除角色的菜单和权限-物理删除
     *
     * @param roleId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByRoleId(String roleId) {
        // 1、角色的菜单
        List<RRoleMenuPrivilegeEntity> menus = rRoleMenuPrivilegeRepository.findByTypeAndRoleId(RRoleMenuPrivilegeTypeEnum.ROLE_MENU.value(), roleId);
        // 2、菜单下的权限
        List<String> menuIds = menus.stream().map(RRoleMenuPrivilegeEntity::getMenuId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(menuIds)) {
            List<RRoleMenuPrivilegeEntity> privileges = rRoleMenuPrivilegeRepository.findByTypeAndMenuIdIn(RRoleMenuPrivilegeTypeEnum.MENU_PRIVILEGE.value(), menuIds);
            menus.addAll(privileges);
        }
        rRoleMenuPrivilegeRepository.deleteAll(menus);
        return true;
    }

    /**
     * 添加菜单权限
     *
     * @param menuId
     * @param privilegeInfos
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addMenuPrivileges(String menuId, List<PrivilegeInfo> privilegeInfos) {
        if (StringUtils.isEmpty(menuId) || privilegeInfos.size() == 0) {
            return false;
        }
       List<RRoleMenuPrivilegeEntity> rs = new ArrayList<>();
        for(PrivilegeInfo p : privilegeInfos) {
            RRoleMenuPrivilegeEntity r = new RRoleMenuPrivilegeEntity();
            r.setId(UUID.randomUUID().toString());
            r.setPrivilegeId(p.getId());
            r.setMenuId(menuId);
            r.setType(RRoleMenuPrivilegeTypeEnum.MENU_PRIVILEGE.value());
            r.setCreateTime(new Date());
            r.setUpdateTime(new Date());
            rs.add(r);
        }
        rRoleMenuPrivilegeRepository.saveAll(rs);
        return true;
    }

    /**
     * 删除菜单权限
     *
     * @param menuId
     * @param privilegeInfos
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeMenuPrivileges(String menuId, List<PrivilegeInfo> privilegeInfos) {
        List<String> pIds = privilegeInfos.stream().map(PrivilegeInfo::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(pIds)) {
            return false;
        }
        // 1、清空无效权限关联关系
        rRoleMenuPrivilegeRepository.deleteNotInPrivilege();
        // 2、删除菜单的权限
        rRoleMenuPrivilegeRepository.deleteByMenuIdAndPrivilegeIdIn(menuId, pIds);
        return true;
    }

    /**
     * 添加角色菜单
     *
     * @param roleId
     * @param menuInfos
     * @return
     */
    @Override
    public boolean addRoleMenus(String roleId, List<MenuInfo> menuInfos) {
        return false;
    }

    /**
     * 删除角色菜单
     *
     * @param roleId
     * @param menuInfos
     * @return
     */
    @Override
    public boolean removeRoleMenus(String roleId, List<PrivilegeInfo> menuInfos) {
        return false;
    }

    private RoleInfo addMenuPrivilege(RoleInfo roleInfo,  List<MenuInfo> menuAll, List<PrivilegeInfo> privilegeAll, List<RRoleMenuPrivilegeEntity> rAll) {
        Map<String, MenuInfo> menus = new HashMap<>();
        // 1、循环所有关系
        for(RRoleMenuPrivilegeEntity r : rAll) {
            if (!r.getRoleId().equals(roleInfo.getId())) {
                continue;
            }
            switch (r.getType()) {
                case 0: // 角色和菜单
                    MenuInfo menu = getMenuInfoById(r.getMenuId(), menuAll);
                    if(null != menu){
                        // 2、设置菜单下的权限
                        List<PrivilegeInfo> privilegeInfos = getPrivilegeInfosByMenuId(menu.getId(), rAll, privilegeAll);
                        menu.setPrivilegeInfos(privilegeInfos);
                        menus.put(menu.getId(),menu);
                    }
                    break;
            }
        }
        // 3、设置角色的菜单和权限
        roleInfo.setMenuInfos(menus.values().stream().collect(Collectors.toList()));
        return roleInfo;
    }

    /**
     * 通过id获取菜单
     * @param id
     * @param menuInfoAll
     * @return
     */
    private MenuInfo getMenuInfoById(String id, List<MenuInfo> menuInfoAll) {
        if (CollectionUtils.isEmpty(menuInfoAll)) {
            return null;
        }
        return menuInfoAll.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    };


    /**
     * 通过id获取权限
     * @param id
     * @param privilegeInfoAll
     * @return
     */
    private PrivilegeInfo getPrivilegeInfoById(String id, List<PrivilegeInfo> privilegeInfoAll) {
        if (CollectionUtils.isEmpty(privilegeInfoAll)) {
            return null;
        }
        return privilegeInfoAll.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    };

    /**
     * 获取菜单的权限实体
     * @param menuId
     * @param rAll
     * @param privilegeInfoAll
     * @return
     */
    private List<PrivilegeInfo> getPrivilegeInfosByMenuId(String menuId, List<RRoleMenuPrivilegeEntity> rAll, List<PrivilegeInfo> privilegeInfoAll) {
        // 1、找出关系表中的菜单下的权限id
        List<String> pIds = rAll.stream().filter(item -> {
            return item.getMenuId().equals(menuId) && item.getType().equals(1);
        }).map(RRoleMenuPrivilegeEntity::getPrivilegeId).collect(Collectors.toList());
        // 2、返回权限数据
        return privilegeInfoAll.stream().filter(item -> pIds.contains(item.getId())).collect(Collectors.toList());
    }

}
