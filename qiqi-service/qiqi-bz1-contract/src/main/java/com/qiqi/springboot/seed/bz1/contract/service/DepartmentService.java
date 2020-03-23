package com.qiqi.springboot.seed.bz1.contract.service;

import com.qiqi.springboot.seed.bz1.contract.model.DepartmentInfo;
import com.qiqi.springboot.seed.bz1.contract.model.PageInfo;

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
     * 分页查找数据
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
}
