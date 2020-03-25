package com.qiqi.springboot.seed.bz1.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author xuguoyuan
 * @description 用户对应的部门和角色信息，一对多的关系在一个表中
 * @date 2020-03-24 16:13
 */
@Entity
@Table(name= "R_USER_DEPARTMENT_ROLE", schema= "qiqi")
public class RUserDepartmentRoleEntity {
    @Id
    @Column(name = "ID", nullable = false, length = 50)
    private String id;
    /**
     * 用户id
     */
    @Column(name = "USER_ID", nullable = false, length = 50)
    private String userId;

    /**
     * 部门id
     */
    @Column(name = "DEPT_ID", nullable = true, length = 50)
    private String deptId;

    /**
     * 角色id
     */
    @Column(name = "ROLE_ID", nullable = true, length = 50)
    private String roleId;

    /**
     * 类型 （0部门/1角色）
     */
    @Column(name = "TYPE")
    private Integer type;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
