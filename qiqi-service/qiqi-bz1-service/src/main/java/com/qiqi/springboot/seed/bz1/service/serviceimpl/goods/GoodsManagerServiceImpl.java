package com.qiqi.springboot.seed.bz1.service.serviceimpl.goods;

import com.qiqi.springboot.seed.bz1.contract.constant.EnableEnum;
import com.qiqi.springboot.seed.bz1.contract.model.goods.GoodsInfo;
import com.qiqi.springboot.seed.bz1.contract.service.goods.GoodsManagerService;
import com.qiqi.springboot.seed.bz1.service.datamappers.goods.GoodsMapper;
import com.qiqi.springboot.seed.bz1.service.repository.goods.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-23 09:20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsManagerServiceImpl implements GoodsManagerService {

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    GoodsMapper goodsMapper;

    /**
     * 保存商品
     *
     * @param goodsInfo
     * @return
     */
    @Override
    public Boolean saveGoods(GoodsInfo goodsInfo) {
        if (StringUtils.isEmpty(goodsInfo.getId())) {
            goodsInfo.setId(UUID.randomUUID().toString());
            goodsInfo.setCreateTime(new Date());
            goodsInfo.setEnable(1);
        }
        goodsInfo.setUpdateTime(new Date());
        goodsRepository.saveAndFlush(goodsMapper.modelToEntity(goodsInfo));
        return true;
    }

    @Override
    public Boolean disableGoods(String id) {
        goodsRepository.disableGoods(id, EnableEnum.DISABLED.value());
        return true;
    }
}
