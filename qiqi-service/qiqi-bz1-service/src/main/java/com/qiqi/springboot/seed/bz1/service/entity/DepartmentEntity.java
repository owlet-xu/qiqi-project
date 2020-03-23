package com.qiqi.springboot.seed.bz1.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author xuguoyuan
 * @description 部门表
 * @date 2020-03-23 09:11
 */
@Entity
@Table(name= "C_DEPARTMENT", schema= "qiqi")
public class DepartmentEntity {
    @Id
    @Column(name = "ID", nullable = false, length = 36)
    private String id;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    /**
     * 父id
     */
    @Column(name = "PARENT_ID", length = 36)
    private String parentId;

    /**
     * 图片id
     */
    @Column(name = "IMG_ID", length = 200)
    private String imgId;

    /**
     * 启用禁用
     */
    @Column(name = "ENABLE")
    private Integer enable;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
