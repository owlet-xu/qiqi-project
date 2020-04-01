package com.qiqi.springboot.seed.bz1.contract.service;

import com.qiqi.springboot.seed.bz1.contract.model.MenuInfo;
import com.qiqi.springboot.seed.bz1.contract.model.PrivilegeInfo;
import com.qiqi.springboot.seed.bz1.contract.model.RoleInfo;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 15:31
 */
public interface RRoleMenuPrivilegeService {

    /**
     * 查询启用的菜单和权限树
     * @return
     */
    List<MenuInfo> findMenuPrivelegeTree();

    /**
     * 查询所有菜单和权限树
     * @return
     */
    List<MenuInfo> findAllMenuPrivelegeTree();
    /**
     * 通过角色Id返回角色的菜单权限树
     * @param roleIds
     * @return
     */
    List<MenuInfo> findRoleMenuPrivelegeTree(List<String> roleIds);

    /**
     * 通过角色Id返回角色的菜单权限列表
     * @param roleIds
     * @return
     */
    List<MenuInfo> findRoleMenuPrivelegeList(List<String> roleIds);

    /**
     * 给加上菜单和权限
     * @return
     */
    List<RoleInfo> addMenuPrivilege(List<RoleInfo> roleInfos);

    /**
     * 删除角色的菜单和权限-物理删除
     */
    boolean deleteByRoleId(String roleId);

    /**
     * 添加菜单权限
     * @param menuId
     * @param privilegeInfos
     * @return
     */
    boolean addMenuPrivileges(String menuId, List<PrivilegeInfo> privilegeInfos);

    /**
     * 删除菜单权限
     * @param menuId
     * @param privilegeInfos
     * @return
     */
    boolean removeMenuPrivileges(String menuId, List<PrivilegeInfo> privilegeInfos);

    /**
     * 添加角色菜单
     * @param roleId
     * @param menuInfos
     * @return
     */
    boolean addRoleMenus(String roleId, List<MenuInfo> menuInfos);

    /**
     * 删除角色菜单
     * @param roleId
     * @param menuInfos
     * @return
     */
    boolean removeRoleMenus(String roleId, List<PrivilegeInfo> menuInfos);
}