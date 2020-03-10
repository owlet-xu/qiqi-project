import { Vue, Component } from 'vue-property-decorator';
// components
import Pagination from '@/components/Pagination/index.vue';
// services
import UserService from '@/api/user-service';
// models
import { PageInfo } from '@/models/page-info';
import { UserInfo } from '@/models/user-info';
@Component({
    components: {
        Pagination
    }
})
export default class UserManager extends Vue {
  private pageInfo: PageInfo<UserInfo> = new PageInfo();
  private loading = true;

  created() {
    this.pageInfo.conditions = new UserInfo();
    this.pageInfo.page = 1;
    this.getUserList();
  }

  getUserList() {
    this.loading = true;
    UserService.findUserListPage(this.getPageConditions()).then((res: PageInfo<UserInfo>) => {
        this.pageInfo.contents = res.contents;
        this.pageInfo.page = res.page + 1;
    }).finally(() => {
        this.loading = false;
    });
  }

  getPageConditions() {
    const condition: PageInfo<UserInfo> = new PageInfo();
    condition.page = this.pageInfo.page - 1;
    condition.size = this.pageInfo.size;
    condition.conditions = this.pageInfo.conditions;
    return condition;
  }

  handleModifyStatus(row: any, type: any) {

  }
}
