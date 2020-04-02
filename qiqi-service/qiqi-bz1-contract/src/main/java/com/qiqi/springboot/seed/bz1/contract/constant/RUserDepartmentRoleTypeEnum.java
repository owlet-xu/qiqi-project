package com.qiqi.springboot.seed.bz1.contract.constant;

/**
 * @author xuguoyuan
 * @description 用户部门角色关系表的类型
 * @date 2020-04-02 13:52
 */
public enum  RUserDepartmentRoleTypeEnum implements EnumValue<Integer>  {

    /**
     * 部门
     */
    DEPARTMENT(0),
    /**
     * 角色
     */
    ROLE(1);

    private Integer type;

    RUserDepartmentRoleTypeEnum(Integer type) {
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
