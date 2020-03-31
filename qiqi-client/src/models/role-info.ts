import { MenuInfo } from './menu-info';

export class RoleInfo {
  id = '';
  /**
   * 编号-唯一标识
   */
  code = -1;
  /**
   * 名称
   */
  name = '';

  defaultData = 0;

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
   * 菜单树
   */
  menuInfos: MenuInfo[] = [];
}
