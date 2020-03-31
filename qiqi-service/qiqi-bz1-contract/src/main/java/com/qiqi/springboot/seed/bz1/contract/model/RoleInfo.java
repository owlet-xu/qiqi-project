package com.qiqi.springboot.seed.bz1.contract.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 13:16
 */
public class RoleInfo {

    @Size(max = 50)
    private String id;
    /**
     * 编号-唯一标识
     */
    private int code;
    /**
     * 名称
     */
    @Size(max = 200)
    @NotBlank
    private String name;

    /**
     * 默认数据
     */
    private int defaultData;

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
     * 菜单树
     */
    private List<MenuInfo> menuInfos;

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

    public List<MenuInfo> getMenuInfos() {
        return menuInfos;
    }

    public void setMenuInfos(List<MenuInfo> menuInfos) {
        this.menuInfos = menuInfos;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public int getDefaultData() {
        return defaultData;
    }

    public void setDefaultData(int defaultData) {
        this.defaultData = defaultData;
    }
}
