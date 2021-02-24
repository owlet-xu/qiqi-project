package com.qiqi.springboot.seed.bz1.contract.service.goods;

import com.qiqi.springboot.seed.bz1.contract.model.goods.GoodsInfo;

/**
 * @Author xuguoyuan
 * @Description // 商品管理
 * @createTime 2021-02-23 18:08:00
 **/
public interface GoodsManagerService {

    /**
     * 保存商品
     * @param goodsInfo
     * @return
     */
    Boolean saveGoods(GoodsInfo goodsInfo);
}
