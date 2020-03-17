import { Vue, Component, Watch } from 'vue-property-decorator';
// components
import Pagination from '@/components/Pagination/index.vue';
import UserForm from './user-form/user-form';
// services
import UserService from '@/api/user-service';
// models
import { PageInfo } from '@/models/page-info';
import { UserInfo } from '@/models/user-info';
// tools
import _ from 'lodash';
import { stringFormatArr } from '@/utils/string-utils';
@Component({
  components: {
    Pagination,
    UserForm
  }
})
export default class UserManager extends Vue {
  private pageInfo: PageInfo<UserInfo> = new PageInfo();
  private loading = true;
  private loadingSave = false;
  private showEditDialog = false;
  private userInfoSelected = new UserInfo();
  private search = '';
  private searchChange: any;
  private showAll = false; // 是否显示所有用户

  created() {
    this.pageInfo.conditions = new UserInfo();
    this.getUserListFirstPage();
    this.searchChange = _.debounce(() => {
      this.getUserListFirstPage();
    }, 500);
  }

  @Watch('showAll')
  showAllChange(newVal: boolean) {
    this.pageInfo.conditions.enable = newVal ? -1 : 1;
    this.getUserListFirstPage();
  }

  getUserListFirstPage() {
    this.pageInfo.page = 1;
    this.getUserList();
  }

  getUserList() {
    this.loading = true;
    UserService.findUserListPage(this.getPageConditions())
      .then((res: PageInfo<UserInfo>) => {
        this.pageInfo.contents = res.contents;
        this.pageInfo.page = res.page + 1;
        this.pageInfo.totalCount = res.totalCount;
        this.pageInfo.size = res.size;
      })
      .finally(() => {
        this.loading = false;
      });
  }

  getPageConditions() {
    const condition: PageInfo<UserInfo> = new PageInfo();
    condition.page = this.pageInfo.page - 1;
    condition.size = this.pageInfo.size;
    condition.conditions = this.pageInfo.conditions;
    condition.search = this.search;
    return condition;
  }

  edit(item: UserInfo) {
    this.userInfoSelected = _.cloneDeep(item);
    this.showEditDialog = true;
  }

  add() {
    this.userInfoSelected = new UserInfo();
    this.showEditDialog = true;
  }

  removeConfirm(item: UserInfo) {
    if (item.enable !== 1) {
      return;
    }
    this.$confirm(stringFormatArr(this.$t('RemoveTip').toString(), ['']), this.$t('Tip').toString(), {
      confirmButtonText: this.$t('Comfirm').toString(),
      cancelButtonText: this.$t('Cancel').toString(),
      type: 'warning'
    }).then(() => {
      this.remove(item.id);
    });
  }

  remove(id: string) {
    UserService.remove(id).then((res: any) => {
      if (res) {
        this.getUserList();
      }
    });
  }

  saveValid() {
    const userForm = this.$refs['userFormRef'] as UserForm;
    userForm.validForm().then((valid: boolean) => {
      if (valid) {
        this.save();
      }
    }).catch(() => {});
  }

  save() {
    this.loadingSave = true;
    this.userInfoSelected.userType = '1';
    UserService.saveUser(this.userInfoSelected)
      .then((res: any) => {
        this.getUserListFirstPage();
        this.showEditDialog = false;
      })
      .finally(() => {
        this.loadingSave = false;
      });
  }

  // 分页改变
  pagination(data: any) {
    this.getUserList();
  }
}
