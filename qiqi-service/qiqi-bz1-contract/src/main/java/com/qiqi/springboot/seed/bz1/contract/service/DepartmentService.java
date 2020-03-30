package com.qiqi.springboot.seed.bz1.contract.service;

import com.qiqi.springboot.seed.bz1.contract.model.DepartmentInfo;
import com.qiqi.springboot.seed.bz1.contract.model.PageInfo;
import com.qiqi.springboot.seed.bz1.contract.model.UserInfo;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-23 09:09
 */
public interface DepartmentService {
    /**
     * 保存部门
     * @param departmentInfo
     * @return
     */
    Boolean saveDepartment(DepartmentInfo departmentInfo);

    /**
     * 分页查找部门
     * @param pageInfo
     * @return
     */
    PageInfo<DepartmentInfo> findDepartmentListPage(PageInfo<DepartmentInfo> pageInfo);

    /**
     * 查询所有部门树
     * @param departmentInfo
     * @return
     */
    List<DepartmentInfo> findDepartmentTree(DepartmentInfo departmentInfo);

    /**
     * 删除部门，包括子部门
     * @param id
     * @return
     */
    boolean disableDepartment(String id);

    /**
     * 获取部门人员,不包括禁用的人员
     * @param pageInfo
     * @return
     */
    PageInfo<UserInfo> getDepartmentUsers(PageInfo<DepartmentInfo> pageInfo);

    /**
     * 获取非本部门人员,不包括禁用的人员
     * @param pageInfo
     * @return
     */
    PageInfo<UserInfo> getOrderDepartmentUsers(PageInfo<DepartmentInfo> pageInfo);

    /**
     * 保存部门关联的人员信息
     * @param departmentInfo
     * @return
     */
    boolean saveDepartmentUsers(DepartmentInfo departmentInfo);

    /**
     * 保存部门关联的人员信息
     * @param departmentInfos
     * @return
     */
    boolean saveDepartmentUsers(List <DepartmentInfo> departmentInfos);

    /**
     * 删除部门关联的人员信息
     * @param departmentInfo
     * @return
     */
    boolean deleteDepartmentUsers(DepartmentInfo departmentInfo);
}
