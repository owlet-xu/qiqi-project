package com.qiqi.springboot.seed.bz1.contract.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-23 09:15
 */
public class DepartmentInfo {

    @Size(max = 50)
    @NotBlank
    private String id;

    /**
     * 名称
     */
    @Size(max = 200)
    @NotBlank
    private String name;

    /**
     * 父id
     */
    @Size(max = 50)
    private String parentId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 树结构的子部门
     */
    List<DepartmentInfo> children;

    /**
     * 启用禁用
     */
    private Integer enable;

    /**
     * 图片id
     */
    @Size(max = 200)
    private String imgId;

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

    public List<DepartmentInfo> getChildren() {
        return children;
    }

    public void setChildren(List<DepartmentInfo> children) {
        this.children = children;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
