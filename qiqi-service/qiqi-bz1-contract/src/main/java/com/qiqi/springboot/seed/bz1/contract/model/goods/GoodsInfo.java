package com.qiqi.springboot.seed.bz1.contract.model.goods;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author xuguoyuan
 * @Description //TODO
 * @createTime 2021-02-23 18:10:00
 **/
public class GoodsInfo {
    @Size(max = 50)
    private String id;

    /**
     * 名称
     */
    @Size(max = 200)
    @NotBlank
    private String name;

    private BigDecimal price;

    private Float discount;

    @Size(max = 500)
    private String description;

    @Size(max = 2000)
    private String detail;

    @Size(max = 200)
    private String detailBg;

    @Size(max = 50)
    private String type1;

    @Size(max = 50)
    private String type2;

    @Size(max = 200)
    private String file1;

    @Size(max = 200)
    private String file2;

    @Size(max = 200)
    private String file3;

    @Size(max = 200)
    private String file4;

    @Size(max = 200)
    private String file5;

    @Size(max = 200)
    private String file6;

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

    public String getDetailBg() {
        return detailBg;
    }

    public void setDetailBg(String detailBg) {
        this.detailBg = detailBg;
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
