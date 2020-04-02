package com.qiqi.springboot.seed.bz1.service.repository;

import com.qiqi.springboot.seed.bz1.service.entity.RRoleMenuPrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    /**
     * 查找角色下的菜单下的权限
     * @param type
     * @param menuIds
     * @return
     */
    List<RRoleMenuPrivilegeEntity> findByTypeAndMenuIdInAndRoleIdIn(int type, List<String> menuIds, List<String> roleIds);

    /**
     * 删除菜单的权限
     * @param menuId
     * @param privilegeIds
     * @return
     */
    int deleteByMenuIdAndPrivilegeIdIn(String menuId, List<String> privilegeIds);

    /**
     * 删除角色的菜单和权限
     * @param roleId
     * @return
     */
    int deleteByRoleId(String roleId);

    /**
     * 清空无效的权限关联关系
     * @return
     */
    @Query("delete from RRoleMenuPrivilegeEntity r where r.privilegeId not in (select distinct p.id from PrivilegeEntity p)")
    @Modifying(clearAutomatically = true)
   int deleteNotInPrivilege();

    /**
     * 清空无效的菜单关联关系
     * @return
     */
    @Query("delete from RRoleMenuPrivilegeEntity r where r.menuId not in (select distinct m.id from MenuEntity m)")
    @Modifying(clearAutomatically = true)
    int deleteNotInMenu();
}
