import { Vue, Component, Watch } from 'vue-property-decorator';
// tools
import { rxevent } from '@/utils/rxevent';
import { stringFormatArr } from '@/utils/string-utils';
// models
import { PageInfo } from '@/models/page-info';
import { UserInfo } from '@/models/user-info';
import { DepartmentInfo } from '@/models/department-info';
import { EventKeys } from '@/common/constant/event-keys';
// components
import Pagination from '@/components/Pagination/index.vue';
import OtherDeptUsers from './other-dept-users/other-dept-users';
// services
import AttachService from '@/api/attach-service';
import DeptService from '@/api/dept-service';

@Component({
  components: {
    Pagination,
    OtherDeptUsers
  }
})
export default class DeptUsers extends Vue {
  private pageInfo: PageInfo<UserInfo> = new PageInfo();
  private currDeptInfo: DepartmentInfo = new DepartmentInfo();
  private search = '';
  private showEditDialog = false;
  private selectedUserIds: Set<string> = new Set(); // 选中的userId

  @Watch('search')
  searchChange(val: string) {
    this.getFistPageDepartmentUsers();
  }

  created() {
    this.deptChangeListen();
  }

  beforeDestroy() {
    rxevent.unsubscribe(EventKeys.deptChange, 'DeptUsers');
  }

  getDeptImgUrl(imgId: string) {
    return AttachService.previewUrl(imgId);
  }

  deptChangeListen() {
    rxevent.subscribe(EventKeys.deptChange, 'DeptUsers', (res: DepartmentInfo) => {
      this.currDeptInfo = res;
      this.getFistPageDepartmentUsers();
    });
  }

  getFistPageDepartmentUsers() {
    this.pageInfo.page = 1;
    this.getDepartmentUsers();
  }

  getDepartmentUsers() {
    DeptService.getDepartmentUsers(this.getPageConditions()).then((res: PageInfo<UserInfo>) => {
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
    dept.id = this.currDeptInfo.id;
    condition.page = this.pageInfo.page - 1;
    condition.size = this.pageInfo.size;
    condition.conditions = dept;
    condition.search = this.search;
    return condition;
  }

  add() {
    this.showEditDialog = true;
  }

  removeConfirm() {
    if (this.selectedUserIds.size === 0) {
      this.$message.warning('请选择用户');
      return Promise.resolve(false);
    }
    this.$confirm(stringFormatArr(this.$t('RemoveTip').toString(), ['']), this.$t('Tip').toString(), {
      confirmButtonText: this.$t('Comfirm').toString(),
      cancelButtonText: this.$t('Cancel').toString(),
      type: 'warning'
    }).then(() => {
      this.remove();
    });
  }

  remove() {
    const data = new DepartmentInfo();
    data.id = this.currDeptInfo.id;
    data.userIds = [...this.selectedUserIds];
    DeptService.deleteDepartmentUsers(data).then((res: any) => {
      if (res) {
        this.getFistPageDepartmentUsers();
        this.$message.success(this.$t('OptionSuccess').toString());
      } else {
        this.$message.success(this.$t('OptionFailed').toString());
      }
    });
  }

  // 添加用户到部门
  addUsers() {
    const userAdd: OtherDeptUsers = this.$refs.userAddRef as OtherDeptUsers;
    userAdd.save().then((res: any) => {
      this.showEditDialog = false;
      this.getFistPageDepartmentUsers();
    });
  }

  // 分页改变
  pagination(data: any) {
    this.getDepartmentUsers();
  }

  /**
   * elementui 多选的key
   * @param row
   */
  getRowKeys(row: UserInfo) {
    return row.id;
  }

  /**
   * 选中用户改变
   */
  handleSelectionChange(selected: UserInfo[]) {
    const ids = selected.map((item: UserInfo) => item.id);
    this.selectedUserIds = new Set(ids);
  }
}
