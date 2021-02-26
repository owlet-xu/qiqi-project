package com.qiqi.springboot.seed.bz1.service.entityfilter;

import com.qiqi.springboot.seed.bz1.contract.model.UserInfo;
import com.qiqi.springboot.seed.bz1.contract.model.goods.GoodsInfo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-16 17:21
 */
public class GoodsEntityFilter {
    private static GoodsEntityFilter instance;

    private GoodsEntityFilter() { }

    public static GoodsEntityFilter getInstance() {
        if (null == instance) {
            instance = new GoodsEntityFilter();
        }
        return instance;
    }

    public GoodsInfo forList(Object[] item) {
        GoodsInfo info = new GoodsInfo();
        info.setId(null == item[0] ? null : String.valueOf(item[0]));
        info.setName(null == item[1] ? null : String.valueOf(item[1]));
        info.setPrice(null == item[2] ? null : (BigDecimal) item[2]);
        info.setDescription(null == item[3] ? null : String.valueOf(item[3]));
        info.setUpdateTime(null == item[4] ? null : (Date) item[4]);
        info.setCreateTime(null == item[5] ? null : (Date) item[5]);
        return info;
    }
}
