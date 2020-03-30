package com.qiqi.springboot.seed.bz1.contract.service;

import com.qiqi.springboot.seed.bz1.contract.model.MenuInfo;
import com.qiqi.springboot.seed.bz1.contract.model.RoleInfo;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 15:31
 */
public interface RRoleMenuPrivilegeService {
    /**
     * 通过角色Id返回角色的菜单权限树
     * @param roleIds
     * @return
     */
    List<MenuInfo> findRoleMenuPrivelegeTree(List<String> roleIds);

    /**
     * 给加上菜单和权限
     * @return
     */
    List<RoleInfo> addMenuPrivilege(List<RoleInfo> roleInfos);

    /**
     * 删除角色的菜单和权限
     */
    boolean deleteByRoleId(String roleId);
}