package com.qiqi.springboot.seed.bz1.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author xuguoyuan
 * @description 用户表
 * @date 2020/3/2
 */
@Entity
@Table(name= "C_USER", schema= "qiqi")
public class UserEntity {
    @Id
    @Column(name = "ID", nullable = false, length = 36)
    private String id;
    /**
     * 姓名
     */
    @Column(name = "NAME", nullable = false, length = 200)
    private String name;
    /**
     * 用户名
     */
    @Column(name = "USER_NAME", nullable = false, length = 200)
    private String userName;
    /**
     * 密码
     */
    @Column(name = "PASSWORD", length = 100)
    private String password;
    /**
     * 手机号
     */
    @Column(name = "MOBILE", length = 100)
    private String mobile;
    /**
     * 邮箱
     */
    @Column(name = "EMAIL", length = 200)
    private String email;
    /**
     * 用户类型 admin/超级管理员
     */
    @Column(name = "USER_TYPE", length = 20)
    private String userType;
    /**
     * 启用禁用
     */
    @Column(name = "ENABLE")
    private Integer enable;
    /**
     * 用户积分
     */
    @Column(name = "USER_INTEGRAL")
    private Integer userIntegral;
    /**
     * 用户等级
     */
    @Column(name = "USER_LEVEL", length = 100)
    private String userLevel;
    /**
     * 用户头像
     */
    @Column(name = "HEAD_IMG", length = 200)
    private String headImg;
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
