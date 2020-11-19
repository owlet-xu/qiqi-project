package com.attach.springboot.attach.bz1.contract.constant;

/**
 * @author xuguoyuan
 * @description 启用禁用枚举
 * @date 2020-03-17 14:29
 */
public enum EnableEnum implements EnumValue<Integer> {

    /**
     * 所有数据
     */
    ALL(-1),
    /**
     * 禁用的数据
     */
    DISABLED(0),
    /**
     * 启用的数据
     */
    ENABLE(1);

    private Integer type;

    EnableEnum(Integer type) {
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
