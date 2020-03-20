package com.qiqi.springboot.seed.bz1.contract.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author xuguoyuan
 * @description 用户信息model
 * @date 2020-03-16 15:17
 */
public class UserInfo {

    @Size(max = 50)
    @NotBlank
    private String id;
    /**
     * 姓名
     */
    @Size(max = 200)
    @NotBlank
    private String name;
    /**
     * 用户名
     */
    @Size(max = 200)
    @NotBlank
    private String userName;
    /**
     * 密码
     */
    @Size(max = 100)
    private String password;
    /**
     * 手机号
     */
    @Size(max = 100)
    private String mobile;
    /**
     * 邮箱
     */
    @Size(max = 200)
    private String email;
    /**
     * 用户类型
     */
    @Size(max = 20)
    private String userType;
    /**
     * 启用禁用
     */
    private Integer enable;
    /**
     * 用户积分
     */
    private Integer userIntegral;
    /**
     * 用户等级
     */
    @Size(max = 100)
    private String userLevel;
    /**
     * 用户头像
     */
    @Size(max = 200)
    private String headImg;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getUserIntegral() {
        return userIntegral;
    }

    public void setUserIntegral(Integer userIntegral) {
        this.userIntegral = userIntegral;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
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

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
