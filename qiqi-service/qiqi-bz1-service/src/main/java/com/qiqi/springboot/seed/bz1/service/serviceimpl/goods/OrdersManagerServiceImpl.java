package com.qiqi.springboot.seed.bz1.service.serviceimpl.goods;

import com.qiqi.springboot.seed.bz1.contract.model.goods.OrdersInfo;
import com.qiqi.springboot.seed.bz1.contract.service.goods.OrdersManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-23 09:20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrdersManagerServiceImpl implements OrdersManagerService {

    /**
     * 下单
     *
     * @param ordersInfo
     * @return
     */
    @Override
    public Boolean saveOrders(OrdersInfo ordersInfo) {
        return null;
    }
}
