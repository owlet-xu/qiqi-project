import { PrivilegeInfo } from './privilege-info';

export class MenuInfo {
  id = '';

  /**
   * 名称
   */
  name = '';

  /**
   * 父id
   */
  parentId = '';

  /**
   * 树深度，从0开始
   */
  deepId = 0;

  /**
   * URL
   */
  url = '';

  /**
   * 排序字段
   */
  orderNum = 0;

  /**
   * 图片id
   */
  imgId = '';

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

  /**
   * 子节点
   */
  children: MenuInfo[] = [];

  /**
   * 菜单下得权限
   */
  privilegeInfos: PrivilegeInfo[] = [];
}
