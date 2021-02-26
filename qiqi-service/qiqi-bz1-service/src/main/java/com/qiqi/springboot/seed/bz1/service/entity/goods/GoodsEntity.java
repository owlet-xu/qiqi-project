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
@Table(name= "B_GOODS", schema= "qiqi")
public class GoodsEntity {

    public GoodsEntity() {
    }

    public GoodsEntity(String id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    @Id
    @Column(name = "ID", nullable = false, length = 36)
    private String id;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "DISCOUNT")
    private Float discount;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "DETAIL", length = 2000)
    private String detail;

    @Column(name = "TYPE1", length = 50)
    private String type1;

    @Column(name = "TYPE2", length = 50)
    private String type2;

    @Column(name = "PIC1", length = 200)
    private String pic1;

    @Column(name = "PIC2", length = 200)
    private String pic2;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
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
