package com.qiqi.springboot.seed.bz1.service.repository;

import com.qiqi.springboot.seed.bz1.service.entity.RRoleMenuPrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 11:49
 */
public interface RRoleMenuPrivilegeRepository extends JpaRepository<RRoleMenuPrivilegeEntity, String> {

    List<RRoleMenuPrivilegeEntity> findByRoleIdIn(List<String> roleIds);

    /**
     * 查询角色的菜单
     * @param roleId
     * @param type
     * @return
     */
    List<RRoleMenuPrivilegeEntity> findByTypeAndRoleId(int type, String roleId);

    /**
     * 查找菜单下的权限
     * @param type
     * @param menuIds
     * @return
     */
    List<RRoleMenuPrivilegeEntity> findByTypeAndMenuIdIn(int type, List<String> menuIds);
}
