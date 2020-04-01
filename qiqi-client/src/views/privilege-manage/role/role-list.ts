import { Vue, Component, Watch } from 'vue-property-decorator';
// services
import RoleService from '@/api/role-service';
// models
import { RoleInfo } from '@/models/role-info';
import { EventKeys } from '@/common/constant/event-keys';
// components
import RoleForm from './role-form/role-form';
import { Tree as ElTree } from 'element-ui';
// tools
import _ from 'lodash';
import { stringFormatArr } from '@/utils/string-utils';
import { rxevent } from '@/utils/rxevent';

@Component({
  components: {
    RoleForm
  }
})
export default class RoleList extends Vue {
  private list: RoleInfo[] = [];
  private roleSelected: RoleInfo = new RoleInfo();
  private roleEditing: RoleInfo = new RoleInfo();
  private showEditDialog = false;
  private saving = false;
  private defaultProps = { children: 'children', label: 'name' };
  private search = '';
  private showAll = false;

  @Watch('search')
  searchChange(val: string) {
    const tree: ElTree = this.$refs['tree'] as ElTree;
    tree.filter(this.search);
  }

  @Watch('showAll')
  showAllChange(val: string) {
    this.search = '';
  }

  get listFilter() {
    return this.list.filter((item: RoleInfo) => {
      if (!this.showAll && !item.enable) {
        return false;
      }
      if (this.search) {
        return item.name.indexOf(this.search) !== -1;
      }
      return true;
    });
  }

  created() {
    this.findList();
  }

  findList() {
    RoleService.findList().then((res: RoleInfo[]) => {
      this.list = res;
      this.$nextTick(() => {
        this.defaultSelectTopOne();
      });
    });
  }

  /**
   * 默认选中角色
   */
  defaultSelectTopOne() {
    const tree: ElTree = this.$refs['tree'] as ElTree;
    if (this.roleSelected.id) {
      tree.setCurrentKey(this.roleSelected.id);
      this.nodeClick(this.roleSelected);
    } else if (this.listFilter.length > 0) {
      tree.setCurrentKey(this.listFilter[0].id);
      this.nodeClick(this.listFilter[0]);
    }
  }

  public nodeClick(data: RoleInfo) {
    this.roleSelected = data;
    rxevent.publish(EventKeys.roleChange, this.roleSelected);
  }

  /**
   * eltree搜索回调函数
   * @param value
   * @param data
   * @param node
   */
  searchTree(value: any, data: RoleInfo, node: any) {
    if (!value) {
      return true;
    }
    return data.name.indexOf(value) !== -1;
  }

  add(item: RoleInfo) {
    this.roleSelected = new RoleInfo();
    this.roleEditing = new RoleInfo();
    this.showEditDialog = true;
  }

  edit(item: RoleInfo) {
    this.roleSelected = item;
    this.roleEditing = _.cloneDeep(item);
    this.showEditDialog = true;
  }

  removeConfirm(item: RoleInfo) {
    if (item && !item.enable) {
      return;
    }
    this.roleSelected = item;
    this.$confirm(stringFormatArr(this.$t('RemoveTip').toString(), ['']), this.$t('Tip').toString(), {
      confirmButtonText: this.$t('Comfirm').toString(),
      cancelButtonText: this.$t('Cancel').toString(),
      type: 'warning'
    }).then(() => {
      this.remove(item.id);
    });
  }

  remove(id: string) {
    RoleService.remove(id).then(() => {
      this.findList();
    });
  }

  save() {
    const form: RoleForm = this.$refs['roleFormRef'] as RoleForm;
    form.saveValid();
  }

  saveSuccess() {
    this.showEditDialog = false;
    this.findList();
  }
}
