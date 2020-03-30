package com.qiqi.springboot.seed.bz1.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author xuguoyuan
 * @description 角色实体
 * @date 2020-03-30 11:33
 */
@Entity
@Table(name= "C_ROLE", schema= "qiqi")
public class RoleEntity {
    @Id
    @Column(name = "ID", nullable = false, length = 36)
    private String id;
    /**
     * 编号-唯一标识
     */
    @Column(name = "CODE")
    private int code;
    /**
     * 姓名
     */
    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
