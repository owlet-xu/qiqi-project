import { Component, Vue, Watch } from 'vue-property-decorator';
// services
import MenuService from '@/api/menu-service';
import RoleService from '@/api/role-service';
// models
import { MenuInfo } from '@/models/menu-info';
import { RoleInfo } from '@/models/role-info';
import { PrivilegeInfo } from '@/models/privilege-info';
import { EventKeys } from '@/common/constant/event-keys';
// components
import { Tree as ElTree } from 'element-ui';
// tools
import { rxevent } from '@/utils/rxevent';
import { stringFormatArr } from '@/utils/string-utils';
import _ from 'lodash';

@Component
export default class RoleMenuTree extends Vue {
  // 菜单权限树
  private menuTree: MenuInfo[] = [];
  private currRoleInfo: RoleInfo = new RoleInfo();
  private defaultProps = { children: 'children', label: 'name', disabled: this.disabledTree };
  private search = '';
  private loadingSave = false;
  private loading = false;
  private canEdit = false;

  @Watch('search')
  searchChange(val: string) {
    const tree: ElTree = this.$refs['tree'] as ElTree;
    tree.filter(this.search);
  }

  created() {
    this.roleChangeListen();
    this.findMenuPrivelegeTree();
  }

  beforeDestroy() {
    rxevent.unsubscribe(EventKeys.roleChange, 'RoleMenuTree');
  }

  roleChangeListen() {
    rxevent.subscribe(EventKeys.roleChange, 'RoleMenuTree', (res: RoleInfo) => {
      this.currRoleInfo = res;
      if (this.menuTree.length === 0) {
        this.findMenuPrivelegeTree();
      } else {
        const tree: ElTree = this.$refs['tree'] as ElTree;
        tree.setCheckedKeys([]);
        this.findRoleMenuPrivelegeList();
      }
    });
  }

  privilege2Menu(tree: MenuInfo[]) {
    tree.forEach((menu: MenuInfo) => {
      if (Array.isArray(menu.privilegeInfos) && menu.privilegeInfos.length > 0) {
        menu.privilegeInfos.forEach((p: PrivilegeInfo) => {
          const item: MenuInfo = new MenuInfo();
          item.id = menu.id + p.id;
          item.name = p.name;
          item.enable = p.enable;
          item.isPrivilege = true;
          item.children = [];
          menu.children = menu.children ? menu.children : [];
          menu.children.push(item);
        });
      }
      if (Array.isArray(menu.children)) {
        this.privilege2Menu(menu.children);
      }
    });
  }

  disabledTree(data: MenuInfo, node: any) {
    if (this.canEdit) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * 从树结构中过滤选中的菜单和权限
   * @param tree
   */
  getSelectedTree(tree: MenuInfo[], keys: string[]) {
    return tree.filter((data: MenuInfo) => {
      if (data.isPrivilege === true) {
        return false; // 权限转成的菜单过滤掉
      } else if (keys.includes(data.id)) {
        if (Array.isArray(data.privilegeInfos)) {
          data.privilegeInfos = data.privilegeInfos.filter((p: PrivilegeInfo) => keys.includes(data.id + p.id));
        }
        if (Array.isArray(data.children)) {
          data.children = this.getSelectedTree(data.children, keys);
        }
        return true;
      } else {
        return false;
      }
    });
  }

  /**
   * 所有启用的菜单和权限树
   */
  findMenuPrivelegeTree() {
    this.loading = true;
    MenuService.findMenuPrivelegeTree().then((res: MenuInfo[]) => {
      this.menuTree = res;
      this.privilege2Menu(this.menuTree);
      this.findRoleMenuPrivelegeList();
    });
  }

  /**
   * 当前角色的菜单和权限,并设置树显示
   */
  async findRoleMenuPrivelegeList() {
    if (!this.currRoleInfo.id) {
      return;
    }
    this.loading = true;
    const menus: MenuInfo[] = await MenuService.findRoleMenuPrivelegeList(this.currRoleInfo.id);
    const keys: string[] = [];
    menus.forEach((menu: MenuInfo) => {
      keys.push(menu.id);
      menu.privilegeInfos.forEach((p: PrivilegeInfo) => {
        keys.push(menu.id + p.id);
      });
    });
    this.loading = false;
    this.canEdit = false;
    this.$nextTick(() => {
      const tree: ElTree = this.$refs['tree'] as ElTree;
      tree.setCheckedKeys(keys);
    });
  }

  edit() {
    this.canEdit = true;
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

  saveConfirm() {
    const tree: ElTree = this.$refs['tree'] as ElTree;
    let keys: string[] = tree.getCheckedKeys();
    const keys2: string[] = tree.getHalfCheckedKeys();
    keys = keys.concat(keys2);
    if (keys.length === 0) {
      this.$message.warning('请勾选菜单和权限');
      return;
    }
    this.$confirm(stringFormatArr(this.$t('RemoveTip').toString(), ['']), this.$t('Tip').toString(), {
      confirmButtonText: this.$t('Comfirm').toString(),
      cancelButtonText: this.$t('Cancel').toString(),
      type: 'warning'
    }).then(() => {
      this.save(keys);
    });
  }

  save(keys: string[]) {
    this.loadingSave = true;
    this.currRoleInfo.menuInfos = this.getSelectedTree(_.cloneDeep(this.menuTree), keys);
    RoleService.saveRoleMenuPrivilege(this.currRoleInfo)
      .then((res: any) => {
        this.canEdit = false;
        this.$message.success(this.$t('OptionSuccess').toString());
      })
      .finally(() => {
        this.loadingSave = false;
      });
  }
}
