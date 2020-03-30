package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.model.MenuInfo;
import com.qiqi.springboot.seed.bz1.contract.service.MenuService;
import com.qiqi.springboot.seed.bz1.service.datamappers.MenuMapper;
import com.qiqi.springboot.seed.bz1.service.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public boolean enableMenu(String id) {
        menuRepository.enableMenu(id, 0);
        return true;
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
}
