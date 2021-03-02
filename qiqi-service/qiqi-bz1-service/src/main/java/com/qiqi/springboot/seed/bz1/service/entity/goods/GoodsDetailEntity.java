package com.qiqi.springboot.seed.bz1.service.entity.goods;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author xuguoyuan
 * @description 商品详情表
 * @date 2020-03-23 09:11
 */
@Entity
@Table(name= "B_GOODS_DETAIL", schema= "qiqi")
public class GoodsDetailEntity {
    @Id
    @Column(name = "ID", nullable = false, length = 50)
    private String id;

    @Column(name = "GOODS_ID", nullable = false, length = 50)
    private String goodsId;

    @Column(name = "DETAIL", nullable = false, length = 4000)
    private String detail;

    @Column(name = "DETAIL_BG", length = 200)
    private String detailBg;

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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getDetailBg() {
        return detailBg;
    }

    public void setDetailBg(String detailBg) {
        this.detailBg = detailBg;
    }
}
