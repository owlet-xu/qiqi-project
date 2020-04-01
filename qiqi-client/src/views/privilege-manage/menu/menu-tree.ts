import { Vue, Component, Watch } from 'vue-property-decorator';
// models
import { MenuInfo } from '@/models/menu-info';
import { EventKeys } from '@/common/constant/event-keys';
// components
import MenuForm from './menu-form';
import { Tree as ElTree } from 'element-ui';
// tools
import _ from 'lodash';
import { stringFormatArr } from '@/utils/string-utils';
import { rxevent } from '@/utils/rxevent';
// services
import MenuService from '@/api/menu-service';

@Component({
  components: {
    MenuForm
  }
})
export default class MenuTree extends Vue {
  // 两份数据是由于需要在前端做数据过滤
  private menusAll: MenuInfo[] = [];
  private menus: MenuInfo[] = [];
  private menuSelected: MenuInfo = new MenuInfo(); // 选中的数据
  private menuEditing: MenuInfo = new MenuInfo(); // 用于编辑的数据
  private showEditDialog = false;
  private saving = false;
  private defaultProps = { children: 'children', label: 'name' };
  private search = '';
  private showAll = false;

  created() {
    this.findMenuTree();
  }

  /**
   * 启用禁用的数据
   */
  get menuFilters() {
    if (this.showAll) {
      return this.menusAll;
    } else {
      return this.filterTree(this.menus);
    }
  }

  filterTree(list: MenuInfo[]) {
    return list.filter((item: MenuInfo) => {
      if (!item.enable) {
        return false;
      } else if (item.children && item.children.length) {
        item.children = this.filterTree(item.children);
      }
      return true;
    });
  }

  @Watch('search')
  searchChange(val: string) {
    const tree: ElTree = this.$refs['tree'] as ElTree;
    tree.filter(this.search);
  }

  @Watch('showAll')
  showAllChange(val: string) {
    this.search = '';
    const tree: ElTree = this.$refs['tree'] as ElTree;
    tree.filter(this.search);
  }

  findMenuTree() {
    MenuService.findAllMenuPrivelegeTree().then((res: MenuInfo[]) => {
      this.menusAll = res;
      this.menus = _.cloneDeep(res);
      this.$nextTick(() => {
        this.defaultSelectTopOne();
      });
    });
  }

  /**
   * 默认选中部门
   */
  defaultSelectTopOne() {
    const tree: ElTree = this.$refs['tree'] as ElTree;
    if (this.menuSelected.id) {
      tree.setCurrentKey(this.menuSelected.id);
      this.nodeClick(this.menuSelected);
    } else if (this.menuFilters.length > 0) {
      tree.setCurrentKey(this.menuFilters[0].id);
      this.nodeClick(this.menuFilters[0]);
    }
  }

  /**
   * eltree搜索回调函数
   * @param value
   * @param data
   * @param node
   */
  searchTree(value: any, data: MenuInfo, node: any) {
    if (!value) {
      return true;
    }
    return data.name.indexOf(value) !== -1;
  }

  public nodeClick(data: MenuInfo) {
    this.menuSelected = data;
    rxevent.publish(EventKeys.menuChange, this.menuSelected);
  }

  add(item?: MenuInfo) {
    this.menuEditing = new MenuInfo();
    if (item && item.id) {
      this.menuSelected = item;
      this.menuEditing.parentId = this.menuSelected.id;
      this.menuEditing.enable = this.menuSelected.enable;
    } else {
      this.menuSelected = new MenuInfo();
      this.menuEditing.parentId = '';
    }
    this.showEditDialog = true;
  }

  edit(item: MenuInfo) {
    this.menuSelected = item;
    this.menuEditing = _.cloneDeep(item);
    this.showEditDialog = true;
  }

  removeConfirm(item: MenuInfo) {
    if (item && !item.enable) {
      return;
    }
    this.menuSelected = item;
    this.$confirm(stringFormatArr(this.$t('RemoveTip').toString(), ['']), this.$t('Tip').toString(), {
      confirmButtonText: this.$t('Comfirm').toString(),
      cancelButtonText: this.$t('Cancel').toString(),
      type: 'warning'
    }).then(() => {
      this.remove(item.id);
    });
  }

  remove(id: string) {
    MenuService.remove(id).then((res: any) => {
      if (res) {
        this.findMenuTree();
      }
    });
  }

  save() {
    const form: MenuForm = this.$refs['menuFormRef'] as MenuForm;
    form.saveValid();
  }

  saveSuccess() {
    this.findMenuTree();
    this.showEditDialog = false;
  }
}
