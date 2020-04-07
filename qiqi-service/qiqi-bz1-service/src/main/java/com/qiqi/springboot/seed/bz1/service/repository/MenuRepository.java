package com.qiqi.springboot.seed.bz1.service.repository;

import com.qiqi.springboot.seed.bz1.service.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 13:04
 */
public interface MenuRepository extends JpaRepository<MenuEntity, String> {

    @Query("update MenuEntity m set m.enable=:enable where m.id in (:ids)")
    @Modifying(clearAutomatically = true)
    int enableMenus(@Param("ids") List<String> ids, @Param("enable") Integer enable);


    /**
     * 查询角色的菜单实体
     * @param roleIds
     * @return
     */
    @Query("select m from MenuEntity m where m.enable=1 and m.id in (select distinct r.menuId from RRoleMenuPrivilegeEntity r where r.roleId in(:roleIds) and r.type=0) order by m.orderNum asc")
    @Modifying(clearAutomatically = true)
    List<MenuEntity> getMenusByRoleIds(@Param("roleIds") List<String> roleIds);

    List<MenuEntity> findByEnableOrderByOrderNumAsc(Integer enable);

    /**
     * 查找父id相同的菜单,启用的
     * @param menuId
     * @return
     */
    @Query("select m from MenuEntity m where m.enable=1 and m.parentId=(select r.parentId from MenuEntity r where r.id=:menuId) order by m.orderNum asc")
    @Modifying(clearAutomatically = true)
    List<MenuEntity> getBySameParentId(@Param("menuId") String menuId);


    /**
     * 查找最大的order
     * @return
     */
    @Query("select max(m.orderNum) from MenuEntity m")
    long getMaxOrder();
}
