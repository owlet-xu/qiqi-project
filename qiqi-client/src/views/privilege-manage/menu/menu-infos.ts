import { Vue, Component } from 'vue-property-decorator';
// components
import OtherMenuPrivileges from '../privilege/other-menu-privileges';
// models
import { MenuInfo } from '@/models/menu-info';
import { PrivilegeInfo } from '@/models/privilege-info';
import { EventKeys } from '@/common/constant/event-keys';
// tools
import { rxevent } from '@/utils/rxevent';
import { stringFormatArr } from '@/utils/string-utils';
// service
import PrivilegeService from '@/api/privilege-service';
import menuService from '@/api/menu-service';
/**
 * 菜单的权限
 */
@Component({
  components: {
    OtherMenuPrivileges
  }
})
export default class MenuInfos extends Vue {
  private list: PrivilegeInfo[] = []; // 菜单的权限
  private currMenuInfo: MenuInfo = new MenuInfo(); // 当前菜单
  private showEditDialog = false;
  private search = '';
  private selectedPrivileges: PrivilegeInfo[] = []; // 选中的待删除的菜单

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
    this.menuChangeListen();
  }

  beforeDestroy() {
    rxevent.unsubscribe(EventKeys.menuChange, 'MenuInfos');
  }

  menuChangeListen() {
    rxevent.subscribe(EventKeys.menuChange, 'MenuInfos', (res: MenuInfo) => {
      this.currMenuInfo = res;
      this.findList();
    });
  }

  findList() {
    this.list = [];
    PrivilegeService.getMenuPrivileges(this.currMenuInfo.id).then((res: PrivilegeInfo[]) => {
      this.list = res;
    });
  }

  add() {
    this.showEditDialog = true;
  }

  savePrivileges() {
    const form: OtherMenuPrivileges = this.$refs['privilegeFormRef'] as OtherMenuPrivileges;
    form.save().then((res: any) => {
      this.showEditDialog = false;
      this.findList();
    });
  }

  saveSuccess() {
    this.showEditDialog = false;
  }

  handleSelectionChange(selected: PrivilegeInfo[]) {
    this.selectedPrivileges = selected;
  }

  removeConfirm() {
    if (this.selectedPrivileges.length === 0) {
      this.$message.warning('请选择权限');
      return;
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
    this.currMenuInfo.privilegeInfos = this.selectedPrivileges;
    menuService.removeMenuPrivileges(this.currMenuInfo).then((res: any) => {
      if (res) {
        this.findList();
        this.$message.success(this.$t('OptionSuccess').toString());
      } else {
        this.$message.success(this.$t('OptionFailed').toString());
      }
    });
  }
}
