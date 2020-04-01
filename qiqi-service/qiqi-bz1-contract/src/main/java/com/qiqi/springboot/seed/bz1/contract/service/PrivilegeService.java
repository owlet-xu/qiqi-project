package com.qiqi.springboot.seed.bz1.contract.service;

import com.qiqi.springboot.seed.bz1.contract.model.MenuInfo;
import com.qiqi.springboot.seed.bz1.contract.model.PrivilegeInfo;
import com.qiqi.springboot.seed.bz1.contract.model.RoleInfo;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 13:15
 */
public interface PrivilegeService {

    boolean save(PrivilegeInfo privilegeInfo);

    /**
     *
     * @param id
     * @return
     */
    boolean enablePrivilege(String id);

    /**
     * 查询所有权限（包括禁用的）
     * @return
     */
    List<PrivilegeInfo> findAll();

    /**
     * 获取菜单的权限-不包括禁用的权限
     * @param menuId
     * @return
     */
    List<PrivilegeInfo> getMenuPrivileges(String menuId);

    /**
     * 获取非菜单的权限-不包括禁用的权限
     * @param menuId
     * @return
     */
    List<PrivilegeInfo> getOtherMenuPrivileges(String menuId);

}
