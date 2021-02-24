package com.qiqi.springboot.seed.bz1.contract.service.goods;

import com.qiqi.springboot.seed.bz1.contract.model.PageInfo;
import com.qiqi.springboot.seed.bz1.contract.model.goods.GoodsInfo;

/**
 * @Author xuguoyuan
 * @Description // 商品查询
 * @createTime 2021-02-23 18:08:00
 **/
public interface GoodsQueryService {

    /**
     * 分页查找商品
     * @param pageInfo
     * @return
     */
    PageInfo<GoodsInfo> findGoodsListPage(PageInfo<GoodsInfo> pageInfo);
}
