package com.qiqi.springboot.seed.bz1.contract.constant;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-17 14:29
 */
public enum UserEnableEnum implements EnumValue<Integer> {

    /**
     * 所有用户
     */
    ALL(-1),
    /**
     * 禁用的用户
     */
    DISABLED(0),
    /**
     * 启用的用户
     */
    ENABLE(1);

    private Integer type;

    UserEnableEnum(Integer type) {
        this.type = type;
    }

    /**
     * 获取枚举值
     *
     * @return 枚举值
     */
    @Override
    public Integer value() {
        return this.type;
    }
}
