package com.qiqi.springboot.seed.bz1.contract.service;

import com.qiqi.springboot.seed.bz1.contract.model.MenuInfo;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 13:15
 */
public interface MenuService {

    /**
     * 保存菜单
     * @param menuInfo
     * @return
     */
    boolean save(MenuInfo menuInfo);

    /**
     * 禁用菜单
     * @param id
     * @return
     */
    boolean enableMenu(String id);

    /**
     * 条件查找菜单树-不包含权限数据
     * @param menuInfo
     * @return
     */
    List<MenuInfo> findMenuTree(MenuInfo menuInfo);
}
