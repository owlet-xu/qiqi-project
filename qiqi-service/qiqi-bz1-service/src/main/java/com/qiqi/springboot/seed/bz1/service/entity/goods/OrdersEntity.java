package com.qiqi.springboot.seed.bz1.service.entity.goods;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xuguoyuan
 * @description 部门表
 * @date 2020-03-23 09:11
 */
@Entity
@Table(name= "B_ORDERS", schema= "qiqi")
public class OrdersEntity {
    @Id
    @Column(name = "ID", nullable = false, length = 50)
    private String id;

    @Column(name = "ORDER_NUM", nullable = false, length = 50)
    private String orderNum;

    @Column(name = "ORDER_TYPE", length = 50)
    private String orderType;

    /**
     * 启用禁用
     */
    @Column(name = "ENABLE")
    private Integer enable;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "CUSTOMER_ID", length = 50)
    private String customerId;

    @Column(name = "PHONE", length = 50)
    private String phone;

    @Column(name = "ADDRESS", length = 500)
    private String address;

    /**
     * 物流单号
     */
    @Column(name = "COURIER_NUM", length = 50)
    private String courierNum;

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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCourierNum() {
        return courierNum;
    }

    public void setCourierNum(String courierNum) {
        this.courierNum = courierNum;
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
