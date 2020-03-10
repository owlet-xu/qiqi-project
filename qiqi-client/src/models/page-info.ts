export class PageInfo<T> {
  page = 0; // 当前页
  size = 20; // 每页条数
  totalCount = 0; // 总数量
  totalPage = 0; // 总页数
  conditions!: T; // 查询条件
  contents: T[] = []; // 条目数组
}
