package com.qiqi.springboot.seed.bz1.contract.model;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 13:17
 */
public class PrivilegeInfo {

    @Size(max = 50)
    private String id;
    /**
     * 名称
     */
    @Size(max = 200)
    private String name;

    private int code;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
}
