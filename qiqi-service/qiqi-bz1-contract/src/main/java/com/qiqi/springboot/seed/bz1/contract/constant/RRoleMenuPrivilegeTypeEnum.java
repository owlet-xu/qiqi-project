package com.qiqi.springboot.seed.bz1.contract.constant;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-31 14:15
 */
public enum  RRoleMenuPrivilegeTypeEnum implements EnumValue<Integer> {
    /**
     * 角色和菜单关系
     */
    ROLE_MENU(0),
    /**
     * 菜单和权限关系
     */
    MENU_PRIVILEGE(1);

    private Integer type;

    RRoleMenuPrivilegeTypeEnum(Integer type) {
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
