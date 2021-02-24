package com.qiqi.springboot.seed.bz1.service.serviceimpl.goods;

import com.qiqi.springboot.seed.bz1.contract.constant.EnableEnum;
import com.qiqi.springboot.seed.bz1.contract.model.PageInfo;
import com.qiqi.springboot.seed.bz1.contract.model.goods.GoodsInfo;
import com.qiqi.springboot.seed.bz1.contract.service.goods.GoodsQueryService;
import com.qiqi.springboot.seed.bz1.service.datamappers.goods.GoodsMapper;
import com.qiqi.springboot.seed.bz1.service.entity.goods.GoodsEntity;
import com.qiqi.springboot.seed.bz1.service.repository.goods.GoodsRepository;
import com.qiqi.springboot.seed.common.util.RepositoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-23 09:20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsQueryServiceImpl implements GoodsQueryService {

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    GoodsMapper goodsMapper;

    /**
     * 分页查找商品
     *
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo<GoodsInfo> findGoodsListPage(PageInfo<GoodsInfo> pageInfo) {
        Specification<GoodsEntity> filters = createSpecification(pageInfo.getConditions(), pageInfo.getSearch());
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = PageRequest.of(pageInfo.getPage(), pageInfo.getSize(), sort);
        Page<GoodsInfo> page = goodsRepository.findAll(filters, pageRequest).map(entity -> {
            GoodsInfo userInfo = goodsMapper.entityToModel(entity);
            return userInfo;
        });
        pageInfo.setContents(page.getContent());
        pageInfo.setTotalCount(page.getTotalElements());
        pageInfo.setTotalPage(page.getTotalPages());
        return  pageInfo;
    }

    private Specification<GoodsEntity> createSpecification(GoodsInfo goodsInfo, String search) {
        List<Predicate> predicatesAdvance = new ArrayList<>(); // 高接搜索，并列
        List<Predicate> predicatesCommon = new ArrayList<>(); // 模糊搜索，or
        Specification<GoodsEntity> filters = (root, query, criteriaBuilder) -> {
            // 没条件，返回
            if (null == goodsInfo && StringUtils.isEmpty(search)) {
                return null;
            }
            // 综合搜搜
            if (!StringUtils.isEmpty(search)) {
                Predicate namePre = criteriaBuilder.like(root.get("name"), RepositoryUtils.prefixForLike(search));
                predicatesCommon.add(namePre);
                Predicate userNamePre = criteriaBuilder.like(root.get("description"), RepositoryUtils.prefixForLike(search));
                predicatesCommon.add(userNamePre);
            }
            if (!StringUtils.isEmpty(goodsInfo.getName())) {
                Predicate namePre = criteriaBuilder.like(root.get("name"),  RepositoryUtils.prefixForLike(goodsInfo.getName()));
                predicatesAdvance.add(namePre);
            }
            if (!StringUtils.isEmpty(goodsInfo.getDescription())) {
                Predicate namePre = criteriaBuilder.like(root.get("description"), RepositoryUtils.prefixForLike(goodsInfo.getDescription()));
                predicatesAdvance.add(namePre);
            }
            // 默认查询启用的商品
            if (null != goodsInfo.getEnable() && goodsInfo.getEnable() != EnableEnum.ALL.value()) {
                Predicate enablePre = criteriaBuilder.equal(root.get("enable"), goodsInfo.getEnable());
                predicatesAdvance.add(enablePre);
            }
            Predicate predicateCommon = criteriaBuilder.or(predicatesCommon.toArray(new Predicate[predicatesCommon.size()]));
            Predicate predicateAdvance = criteriaBuilder.and(predicatesAdvance.toArray(new Predicate[predicatesAdvance.size()]));

            if (predicatesCommon.size() > 0 && predicatesAdvance.size() > 0) {
                Predicate[] predicate = { predicateCommon, predicateAdvance };
                return criteriaBuilder.and(predicate);
            } else if (predicatesCommon.size() > 0) {
                return predicateCommon;
            } else if (predicatesAdvance.size() > 0) {
                return predicateAdvance;
            } else {
                return null;
            }
        };
        return filters;
    }
}
