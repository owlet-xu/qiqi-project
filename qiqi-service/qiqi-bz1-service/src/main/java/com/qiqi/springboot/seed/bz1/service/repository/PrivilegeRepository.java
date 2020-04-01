package com.qiqi.springboot.seed.bz1.service.repository;

import com.qiqi.springboot.seed.bz1.service.entity.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 13:07
 */
public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, String> {

    List<PrivilegeEntity> findByIdIn(List<String> ids);

    @Query("update PrivilegeEntity p set p.enable=:enable where p.id=:id")
    @Modifying(clearAutomatically = true)
    int enablePrivilege(@Param("id") String id, @Param("enable") int enable);

    /**
     * 查询菜单的权限实体
     * @param menuIds
     * @return
     */
    @Query("select p from PrivilegeEntity p where p.enable=1 and p.id in (select distinct r.privilegeId from RRoleMenuPrivilegeEntity r where r.menuId in(:menuIds) and r.type=1)")
    @Modifying(clearAutomatically = true)
    List<PrivilegeEntity> getPrivilegesByMenuIds(@Param("menuIds") List<String> menuIds);

    /**
     * 查询其他菜单的权限实体
     * @param menuIds
     * @return
     */
    @Query("select p from PrivilegeEntity p where p.enable=1 and p.id not in (select distinct r.privilegeId from RRoleMenuPrivilegeEntity r where r.menuId in(:menuIds) and r.type=1)")
    @Modifying(clearAutomatically = true)
    List<PrivilegeEntity> getOtherMenuPrivileges(@Param("menuIds") List<String> menuIds);

}
