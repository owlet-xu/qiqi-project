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

    @Query("update MenuEntity m set m.enable=:enable where m.id=:id")
    @Modifying(clearAutomatically = true)
    int enableMenu(@Param("id") String id, @Param("enable") Integer enable);


    /**
     * 查询角色的菜单
     * @param roleIds
     * @return
     */
    @Query("select m from MenuEntity m where m.enable=1 and m.id in (select distinct r.menuId from RRoleMenuPrivilegeEntity r where r.roleId in(:roleIds) and r.type=0)")
    @Modifying(clearAutomatically = true)
    List<MenuEntity> getMenusByRoleIds(@Param("roleIds") List<String> roleIds);
}
