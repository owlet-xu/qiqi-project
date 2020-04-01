package com.qiqi.springboot.seed.bz1.contract.service;

import com.qiqi.springboot.seed.bz1.contract.model.RoleInfo;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 13:15
 */
public interface RoleService {

    /**
     * 保存角色
     * @param roleInfo
     * @return
     */
    boolean save(RoleInfo roleInfo);

    /**
     * 保存角色的菜单和权限数据
     * @param roleInfo
     * @return
     */
    boolean saveRoleMenuPrivilege(RoleInfo roleInfo);

    /**
     * 禁用角色
     * @param id
     * @return
     */
    boolean enableRole(String id);

    /**
     * 给某个用户添加角色
     * @param userId
     * @param roleIds
     * @return
     */
    boolean addUserRoles(String userId, List<String> roleIds);

    /**
     * 条件查找所有角色列表
     * @return
     */
    List<RoleInfo> findList(RoleInfo roleInfo);

    boolean disableRole(String id);
}
