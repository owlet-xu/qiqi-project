export class DepartmentInfo {
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
   * 创建时间
   */
  createTime!: Date;

  /**
   * 树结构的子部门
   */
  children: DepartmentInfo[] = [];

  /**
   * 图片id
   */
  imgId = '';

  enable = 1;
}
