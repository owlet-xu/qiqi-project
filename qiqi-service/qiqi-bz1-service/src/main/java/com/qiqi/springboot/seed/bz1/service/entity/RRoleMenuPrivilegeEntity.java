package com.qiqi.springboot.seed.bz1.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 11:42
 */
@Entity
@Table(name= "R_ROLE_MENU_PRIVILEGE", schema= "qiqi")
public class RRoleMenuPrivilegeEntity {
    @Id
    @Column(name = "ID", nullable = false, length = 36)
    private String id;

    @Column(name = "ROLE_ID", length = 36)
    private String roleId;

    @Column(name = "MENU_ID", length = 36)
    private String menuId;

    @Column(name = "PRIVILEGE_ID", length = 36)
    private String privilegeId;

    /**
     * 类型 （0角色和菜单关系/1菜单和权限关系）
     */
    @Column(name = "TYPE")
    private Integer type;

    /**
     * 更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
