package com.qiqi.springboot.seed.bz1.contract.constant;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-16 16:47
 */
public enum  UserTypeEnum implements EnumValue<String> {
    /**
     * 超级管理员
     */
    ADMIN("0"),
    /**
     * 普通用户
     */
    COMMON("1");

    private String type;

    UserTypeEnum(String type) {
        this.type = type;
    }

    /**
     * 获取枚举值
     *
     * @return 枚举值
     */
    @Override
    public String value() {
        return this.type;
    }
}
