package com.qiqi.springboot.seed.bz1.service.repository.goods;

import com.qiqi.springboot.seed.bz1.service.entity.goods.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-23 09:17
 */
public interface OrdersRepository extends JpaRepository<OrdersEntity, String> {

}
