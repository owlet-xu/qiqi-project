package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.model.MenuInfo;
import com.qiqi.springboot.seed.bz1.contract.service.MenuService;
import com.qiqi.springboot.seed.bz1.contract.service.RRoleMenuPrivilegeService;
import com.qiqi.springboot.seed.bz1.service.datamappers.MenuMapper;
import com.qiqi.springboot.seed.bz1.service.entity.MenuEntity;
import com.qiqi.springboot.seed.bz1.service.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 13:54
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    RRoleMenuPrivilegeService rRoleMenuPrivilegeService;
    /**
     * 保存菜单
     *
     * @param menuInfo
     * @return
     */
    @Override
    public boolean save(MenuInfo menuInfo) {
        if (StringUtils.isEmpty(menuInfo.getId())) {
            menuInfo.setId(UUID.randomUUID().toString());
            menuInfo.setCreateTime(new Date());
        }
        menuInfo.setUpdateTime(new Date());
        menuRepository.saveAndFlush(menuMapper.modelToEntity(menuInfo));
        return true;
    }

    /**
     * 禁用菜单
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disableMenu(String id) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        List<MenuEntity> listAll = menuRepository.findAll();
        List<String> childrenIds = new ArrayList<>();
        getChildrenIds(listAll, id, childrenIds);
        childrenIds.add(id);
        menuRepository.enableMenus(childrenIds, 0);
        return true;
    }

    /**
     * 获取树的子节点id
     * @param listAll 所有数据
     * @param parentId 父id
     * @param childrenIds 子id集合，返回的数据
     */
    private void getChildrenIds(List<MenuEntity> listAll, String parentId, List<String> childrenIds) {
        if (StringUtils.isEmpty(parentId) || CollectionUtils.isEmpty(listAll)) {
            return;
        }
        // 过滤出子
        List<MenuEntity> children = listAll.stream().filter(itemAll -> parentId.equals(itemAll.getParentId())).collect(Collectors.toList());
        // 循环子，获取id
        for(MenuEntity item : children) {
            childrenIds.add(item.getId());
            getChildrenIds(listAll, item.getId(),childrenIds );
        }
    }

    /**
     * 条件查找菜单树-不包含权限数据
     *
     * @param menuInfo
     * @return
     */
    @Override
    public List<MenuInfo> findMenuTree(MenuInfo menuInfo) {

        return null;
    }

    /**
     * 添加菜单权限
     *
     * @param menuInfo
     * @return
     */
    @Override
    public boolean addMenuPrivileges(MenuInfo menuInfo) {
        return rRoleMenuPrivilegeService.addMenuPrivileges(menuInfo.getId(), menuInfo.getPrivilegeInfos());
    }

    /**
     * 删除菜单权限
     *
     * @param menuInfo
     * @return
     */
    @Override
    public boolean removeMenuPrivileges(MenuInfo menuInfo) {
        return rRoleMenuPrivilegeService.removeMenuPrivileges(menuInfo.getId(), menuInfo.getPrivilegeInfos());
    }
}
