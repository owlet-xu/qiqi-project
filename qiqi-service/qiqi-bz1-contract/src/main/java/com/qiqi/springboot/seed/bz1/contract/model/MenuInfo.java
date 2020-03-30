package com.qiqi.springboot.seed.bz1.contract.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 13:17
 */
public class MenuInfo {
    @Size(max = 50)
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
     * 树深度，从0开始
     */
    private Integer deepId;

    /**
     * URL
     */
    @Size(max = 1000)
    private String url;


    /**
     * 排序字段
     */
    private long orderNum;

    /**
     * 图片id
     */
    @Size(max = 200)
    private String imgId;

    /**
     * 启用禁用
     */
    private Integer enable;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 子节点
     */
    private List<MenuInfo> children;

    /**
     * 菜单下得权限
     */
    private List<PrivilegeInfo> privilegeInfos;

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

    public Integer getDeepId() {
        return deepId;
    }

    public void setDeepId(Integer deepId) {
        this.deepId = deepId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<MenuInfo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuInfo> children) {
        this.children = children;
    }

    public List<PrivilegeInfo> getPrivilegeInfos() {
        return privilegeInfos;
    }

    public void setPrivilegeInfos(List<PrivilegeInfo> privilegeInfos) {
        this.privilegeInfos = privilegeInfos;
    }
}
