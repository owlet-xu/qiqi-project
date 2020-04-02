package com.qiqi.springboot.seed.bz1.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 11:34
 */
@Entity
@Table(name= "C_MENU", schema= "qiqi")
public class MenuEntity {

    @Id
    @Column(name = "ID", nullable = false, length = 36)
    private String id;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    /**
     * 菜单的国际化key或唯一标识
     */
    @Column(name = "CODE", nullable = false, length = 200)
    private String code;

    /**
     * 父id
     */
    @Column(name = "PARENT_ID", length = 36)
    private String parentId;

    /**
     * 树深度，从0开始
     */
    @Column(name = "DEEP_ID")
    private Integer deepId;

    /**
     * URL
     */
    @Column(name = "URL", length = 1000)
    private String url;


    /**
     * 排序字段
     */
    @Column(name = "ORDER_NUM")
    private long orderNum;

    /**
     * 图片id
     */
    @Column(name = "IMG_ID", length = 200)
    private String imgId;

    /**
     * 图标名称
     */
    @Column(name = "ICON", length = 200)
    private String icon;

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

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
