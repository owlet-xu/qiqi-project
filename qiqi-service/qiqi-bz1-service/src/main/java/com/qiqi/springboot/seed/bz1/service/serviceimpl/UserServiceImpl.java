package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.model.*;
import com.qiqi.springboot.seed.bz1.contract.service.UserService;
import com.qiqi.springboot.seed.bz1.service.datamappers.UserMapper;
import com.qiqi.springboot.seed.bz1.service.entity.UserEntity;
import com.qiqi.springboot.seed.bz1.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author xgy
 * @description
 * @date 2018/9/6
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public PageInfo<UserInfo> findUserListPage(PageInfo<UserInfo> pageInfo) {
        Specification<UserEntity> filters = createSpecification(pageInfo.getConditions());
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = PageRequest.of(pageInfo.getPage(), pageInfo.getSize(), sort);
        Page<UserInfo> page = userRepository.findAll(filters, pageRequest).map(userMapper :: entityToModel);
        pageInfo.setContents(page.getContent());
        pageInfo.setTotalCount(page.getTotalElements());
        pageInfo.setTotalPage(page.getTotalPages());
        return  pageInfo;
    }

    @Override
    public Boolean saveUser(UserInfo userInfo) {
        if (StringUtils.isEmpty(userInfo.getId())) {
            userInfo.setId(UUID.randomUUID().toString());
        }
        userRepository.save(userMapper.modelToEntity(userInfo));
        return true;
    }

    private Specification<UserEntity> createSpecification(UserInfo userInfo) {
        List<Predicate> predicates = new ArrayList<>();
            Specification<UserEntity> filters = (root, query, criteriaBuilder) -> {
            if (null == userInfo) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
            // 过滤姓名
            if (!StringUtils.isEmpty(userInfo.getName())) {
                Predicate namePre = criteriaBuilder.equal(root.get("name"), userInfo.getName());
                predicates.add(namePre);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return filters;
    }
}
