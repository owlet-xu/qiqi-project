import { Vue, Component, Prop, Watch } from 'vue-property-decorator';
// models
import { PageInfo } from '@/models/page-info';
import { UserInfo } from '@/models/user-info';
import { DepartmentInfo } from '@/models/department-info';
// services
import DeptService from '@/api/dept-service';
// components
import Pagination from '@/components/Pagination/index.vue';

@Component({
  components: {
    Pagination
  }
})
export default class OtherDeptUsers extends Vue {
  @Prop({ default: '' })
  deptId!: string;
  private pageInfo: PageInfo<UserInfo> = new PageInfo();
  private search = '';
  private selectedUserIds: Set<string> = new Set(); // 选中的userId

  created() {
    this.getFirstPage();
  }

  @Watch('search')
  searchChange(val: string) {
    this.getFirstPage();
  }

  getFirstPage() {
    this.pageInfo.page = 1;
    this.getList();
  }

  getList() {
    DeptService.getOrderDepartmentUsers(this.getPageConditions()).then((res: PageInfo<UserInfo>) => {
      this.pageInfo.contents = res.contents;
      this.pageInfo.page = res.page + 1;
      this.pageInfo.totalCount = res.totalCount;
      this.pageInfo.size = res.size;
    });
  }
  /**
   * 获取查询条件
   */
  getPageConditions() {
    const dept = new DepartmentInfo();
    const condition: PageInfo<DepartmentInfo> = new PageInfo();
    dept.id = this.deptId;
    condition.page = this.pageInfo.page - 1;
    condition.size = this.pageInfo.size;
    condition.conditions = dept;
    condition.search = this.search;
    return condition;
  }

  // 分页改变
  pagination(data: any) {
    this.getList();
  }

  /**
   * 选中用户改变
   */
  handleSelectionChange(selected: UserInfo[]) {
    const ids = selected.map((item: UserInfo) => item.id);
    this.selectedUserIds = new Set(ids);
  }

  /**
   * elementui 多选的key
   * @param row
   */
  getRowKeys(row: UserInfo) {
    return row.id;
  }

  /**
   * 保存用户到部门
   */
  public save(): Promise<any> {
    if (this.selectedUserIds.size === 0) {
      this.$message.warning('请选择用户');
      return Promise.resolve(false);
    }
    const data = new DepartmentInfo();
    data.id = this.deptId;
    data.userIds = [...this.selectedUserIds];
    return DeptService.saveDepartmentUsers(data);
  }
}
