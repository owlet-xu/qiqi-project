package com.qiqi.springboot.seed.bz1.contract.service.goods;

import com.qiqi.springboot.seed.bz1.contract.model.goods.OrdersInfo;

/**
 * @Author xuguoyuan
 * @Description // 订单管理
 * @createTime 2021-02-23 18:08:00
 **/
public interface OrdersManagerService {

    /**
     * 下单
     * @param ordersInfo
     * @return
     */
    Boolean saveOrders(OrdersInfo ordersInfo);
}
