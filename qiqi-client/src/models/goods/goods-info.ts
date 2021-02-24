
export class GoodsInfo {
  id = '';

  name = '';

  price = 0;

  discount = 1;

  description = '';

  detail = '';

  type1 = '';

  type2 = '';

  pic1 = '';

  pic2 = '';

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
