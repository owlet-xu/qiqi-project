package com.qiqi.springboot.seed.bz1.contract.service.goods;

import com.qiqi.springboot.seed.bz1.contract.model.PageInfo;
import com.qiqi.springboot.seed.bz1.contract.model.goods.OrdersInfo;

/**
 * @Author xuguoyuan
 * @Description // 订单查询
 * @createTime 2021-02-23 18:08:00
 **/
public interface OrdersQueryService {

    /**
     * 分页查找订单
     * @param pageInfo
     * @return
     */
    PageInfo<OrdersInfo> findGoodsListPage(PageInfo<OrdersInfo> pageInfo);

}
