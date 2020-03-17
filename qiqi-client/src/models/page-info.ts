export class PageInfo<T> {
  page = 0; // 当前页
  size = 10; // 每页条数
  sizes = [10, 20, 30, 50]; // 可调整分页
  totalCount = 0; // 总数量
  totalPage = 0; // 总页数
  conditions!: T; // 查询条件
  contents: T[] = []; // 条目数组
  search = ''; // 综合搜索文字
}
