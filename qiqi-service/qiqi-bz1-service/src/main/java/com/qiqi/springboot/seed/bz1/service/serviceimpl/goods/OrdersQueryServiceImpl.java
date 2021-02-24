package com.qiqi.springboot.seed.bz1.service.serviceimpl.goods;

import com.qiqi.springboot.seed.bz1.contract.model.PageInfo;
import com.qiqi.springboot.seed.bz1.contract.model.goods.OrdersInfo;
import com.qiqi.springboot.seed.bz1.contract.service.goods.OrdersManagerService;
import com.qiqi.springboot.seed.bz1.contract.service.goods.OrdersQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-23 09:20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrdersQueryServiceImpl implements OrdersQueryService {

    /**
     * 分页查找订单
     *
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo<OrdersInfo> findGoodsListPage(PageInfo<OrdersInfo> pageInfo) {
        return null;
    }
}
