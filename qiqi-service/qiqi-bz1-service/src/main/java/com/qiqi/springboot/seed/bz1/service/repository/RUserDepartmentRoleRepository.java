package com.qiqi.springboot.seed.bz1.service.repository;

import com.qiqi.springboot.seed.bz1.service.entity.RUserDepartmentRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-24 16:13
 */
public interface RUserDepartmentRoleRepository extends JpaRepository<RUserDepartmentRoleEntity, String> {

    List<RUserDepartmentRoleEntity> findByType(Integer type);

    @Query("select r.deptId from RUserDepartmentRoleEntity r where r.type=0 and r.userId=:userId")
    @Modifying(clearAutomatically = true)
    List<String> getDeptIdsByUserId(@Param("userId") String userId);

    @Query("select r.deptId from RUserDepartmentRoleEntity r where r.type=1 and r.userId=:userId")
    @Modifying(clearAutomatically = true)
    List<String> getRoleIdsByUserId(@Param("userId") String userId);

    @Query("select r.userId from RUserDepartmentRoleEntity r where r.type=0 and r.deptId=:deptId")
    @Modifying(clearAutomatically = true)
    List<String> getUserIdsByDeptId(@Param("deptId") String deptId);

    @Query("select r.userId from RUserDepartmentRoleEntity r where r.type=1 and r.roleId=:roleId")
    @Modifying(clearAutomatically = true)
    List<String> getUserIdsByRoleId(@Param("roleId") String roleId);

    @Query("delete from RUserDepartmentRoleEntity r where r.deptId=:deptId and r.userId in :userIds")
    @Modifying(clearAutomatically = true)
    int deleteDepartmentUsers(@Param("deptId") String deptId, @Param("userIds") List<String> userIds);

    @Query("delete from RUserDepartmentRoleEntity r where r.type=0 and r.userId=:userId")
    @Modifying(clearAutomatically = true)
    int deleteDepartmentByUserId(@Param("userId") String userId);

    @Query("delete from RUserDepartmentRoleEntity r where r.type=1 and r.userId=:userId")
    @Modifying(clearAutomatically = true)
    int deleteRoleByUserId(@Param("userId") String userId);

    /**
     * 获取已关联部门的用户id
     * @return
     */
    @Query("select distinct r.userId from RUserDepartmentRoleEntity r where r.type=0")
    @Modifying(clearAutomatically = true)
    List<String> getUserIdsForDept();
}
