package com.qiqi.springboot.seed.bz1.contract.constant;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-31 14:15
 */
public enum  RRoleMenuPrivilegeTypeEnum implements EnumValue<Integer> {
    /**
     * 每个角色下的菜单
     */
    ROLE_MENU(0),
    /**
     * 每个菜单下的权限
     */
    MENU_PRIVILEGE(1),

    /**
     * 每个角色下的菜单的权限
     */
    ROLE_MENU_PRIVILEGE(2);


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
