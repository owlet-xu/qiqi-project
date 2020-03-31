/**
 * 权限的实体
 */
export class PrivilegeInfo {
  id = '';
  /**
   * 名称
   */
  name = '';

  code = -1;

  /**
   * 启用禁用
   */
  enable = 1;

  /**
   * 更新时间
   */
  updateTime!: Date;
  /**
   * 创建时间
   */
  createTime!: Date;
}
