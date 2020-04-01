export class RRoleMenuPrivilegeInfo {
  id = '';

  roleId = '';

  menuId = '';

  privilegeId = '';

  /**
   * 类型 （0角色和菜单关系/1菜单和权限关系）
   */
  type = -1;

  /**
   * 更新时间
   */
  updateTime!: Date;
  /**
   * 创建时间
   */
  createTime!: Date;
}
