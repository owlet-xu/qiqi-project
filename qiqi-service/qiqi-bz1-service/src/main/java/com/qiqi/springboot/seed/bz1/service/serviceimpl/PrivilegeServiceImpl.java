package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.model.MenuInfo;
import com.qiqi.springboot.seed.bz1.contract.model.PrivilegeInfo;
import com.qiqi.springboot.seed.bz1.contract.service.PrivilegeService;
import com.qiqi.springboot.seed.bz1.service.datamappers.PrivilegeMapper;
import com.qiqi.springboot.seed.bz1.service.repository.PrivilegeRepository;
import com.qiqi.springboot.seed.bz1.service.repository.RRoleMenuPrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author xuguoyuan
 * @description 权限
 * @date 2020-03-30 13:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Autowired
    PrivilegeMapper privilegeMapper;

    @Autowired
    RRoleMenuPrivilegeRepository rRoleMenuPrivilegeRepository;

    @Override
    public boolean save(PrivilegeInfo privilegeInfo) {
        if (StringUtils.isEmpty(privilegeInfo.getId())) {
            privilegeInfo.setId(UUID.randomUUID().toString());
            privilegeInfo.setCreateTime(new Date());
        }
        privilegeInfo.setUpdateTime(new Date());
        privilegeRepository.saveAndFlush(privilegeMapper.modelToEntity(privilegeInfo));
        return true;
    }

    @Override
    public boolean enablePrivilege(String id) {
        privilegeRepository.enablePrivilege(id, 0);
        return true;
    }

    /**
     * 查询所有权限（包括禁用的）
     *
     * @return
     */
    @Override
    public List<PrivilegeInfo> findAll() {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return privilegeMapper.entitiesToModels(privilegeRepository.findAll(sort));
    }

    /**
     * 获取菜单的权限-不包括禁用的权限
     *
     * @param menuId
     * @return
     */
    @Override
    public List<PrivilegeInfo> getMenuPrivileges(String menuId) {
        String[] menuids = {menuId};
        return privilegeMapper.entitiesToModels( privilegeRepository.getPrivilegesByMenuIds(Arrays.asList(menuId)));
    }

    /**
     * 获取非菜单的权限-不包括禁用的权限
     *
     * @param menuId
     * @return
     */
    @Override
    public List<PrivilegeInfo> getOtherMenuPrivileges(String menuId) {
        String[] menuids = {menuId};
        return privilegeMapper.entitiesToModels( privilegeRepository.getOtherMenuPrivileges(Arrays.asList(menuId)));
    }
}
