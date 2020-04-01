import { Vue, Component, Prop, Watch } from 'vue-property-decorator';
// models
import { PrivilegeInfo } from '@/models/privilege-info';
import { MenuInfo } from '@/models/menu-info';
// services
import PrivilegeService from '@/api/privilege-service';
import menuService from '@/api/menu-service';

/**
 * 其他菜单的权限
 */
@Component
export default class OtherMenuPrivileges extends Vue {
  @Prop({ default: '' })
  menuId!: string;
  private list: PrivilegeInfo[] = [];
  private search = '';
  private selectedPrivileges: PrivilegeInfo[] = []; // 选中的权限

  get listFilter() {
    return this.list.filter((item: PrivilegeInfo) => {
      if (!this.search) {
        return true;
      } else {
        return item.name.indexOf(this.search) !== -1;
      }
    });
  }

  created() {
    this.getList();
  }

  getList() {
    PrivilegeService.getOtherMenuPrivileges(this.menuId).then((res: PrivilegeInfo[]) => {
      this.list = res;
    });
  }

  /**
   * 选中用户改变
   */
  handleSelectionChange(selected: PrivilegeInfo[]) {
    this.selectedPrivileges = selected;
  }

  /**
   * 保存菜单的权限
   */
  public save(): Promise<any> {
    if (this.selectedPrivileges.length === 0) {
      this.$message.warning('请选择权限');
      return Promise.resolve(false);
    }
    const menu = new MenuInfo();
    menu.id = this.menuId;
    menu.privilegeInfos = this.selectedPrivileges;
    return menuService.addMenuPrivileges(menu);
  }
}
