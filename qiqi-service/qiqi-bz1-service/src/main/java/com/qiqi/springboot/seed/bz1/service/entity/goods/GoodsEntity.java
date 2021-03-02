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

    @Column(name = "TYPE1", length = 50)
    private String type1;

    @Column(name = "TYPE2", length = 50)
    private String type2;

    @Column(name = "FILE1", length = 200)
    private String file1;

    @Column(name = "FILE2", length = 200)
    private String file2;

    @Column(name = "FILE3", length = 200)
    private String file3;

    @Column(name = "FILE4", length = 200)
    private String file4;

    @Column(name = "FILE5", length = 200)
    private String file5;

    @Column(name = "FILE6", length = 200)
    private String file6;

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

    public String getFile1() {
        return file1;
    }

    public void setFile1(String file1) {
        this.file1 = file1;
    }

    public String getFile2() {
        return file2;
    }

    public void setFile2(String file2) {
        this.file2 = file2;
    }

    public String getFile3() {
        return file3;
    }

    public void setFile3(String file3) {
        this.file3 = file3;
    }

    public String getFile4() {
        return file4;
    }

    public void setFile4(String file4) {
        this.file4 = file4;
    }

    public String getFile5() {
        return file5;
    }

    public void setFile5(String file5) {
        this.file5 = file5;
    }

    public String getFile6() {
        return file6;
    }

    public void setFile6(String file6) {
        this.file6 = file6;
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
