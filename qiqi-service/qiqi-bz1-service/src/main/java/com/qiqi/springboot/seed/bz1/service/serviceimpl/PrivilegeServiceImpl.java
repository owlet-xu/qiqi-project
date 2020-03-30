package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.model.MenuInfo;
import com.qiqi.springboot.seed.bz1.contract.model.PrivilegeInfo;
import com.qiqi.springboot.seed.bz1.contract.service.PrivilegeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 13:52
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Override
    public boolean save(PrivilegeInfo privilegeInfo) {
        return false;
    }

    @Override
    public boolean enablePrivilege(String id) {
        return false;
    }
}
