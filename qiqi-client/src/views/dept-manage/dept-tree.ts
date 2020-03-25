import { Vue, Component, Watch } from 'vue-property-decorator';
// models
import { DepartmentInfo } from '@/models/department-info';
import { EventKeys } from '@/common/constant/event-keys';
// components
import DeptForm from './dept-form/dept-form';
import { Tree as ElTree } from 'element-ui';
// tools
import _ from 'lodash';
import { stringFormatArr } from '@/utils/string-utils';
import { rxevent } from '@/utils/rxevent';
// services
import DeptService from '@/api/dept-service';

@Component({
  components: {
    DeptForm
  }
})
export default class DeptTree extends Vue {
  // 两份数据是由于需要在前端做数据过滤
  private deptsAll: DepartmentInfo[] = [];
  private depts: DepartmentInfo[] = [];
  private deptSelected: DepartmentInfo = new DepartmentInfo(); // 选中的数据
  private deptEditing: DepartmentInfo = new DepartmentInfo(); // 用于编辑的数据
  private showEditDialog = false;
  private saving = false;
  private defaultProps = { children: 'children', label: 'name' };
  private search = '';
  private showAll = false;

  created() {
    this.findDepartmentTree();
  }

  /**
   * 启用禁用的数据
   */
  get deptFilters() {
    if (this.showAll) {
      return this.deptsAll;
    } else {
      return this.filterTree(this.depts);
    }
  }

  filterTree(list: DepartmentInfo[]) {
    return list.filter((item: DepartmentInfo) => {
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

  findDepartmentTree() {
    DeptService.findDepartmentTree().then((res: DepartmentInfo[]) => {
      this.deptsAll = res;
      this.depts = _.cloneDeep(res);
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
    if (this.deptSelected.id) {
      tree.setCurrentKey(this.deptSelected.id);
      this.nodeClick(this.deptSelected);
    } else if (this.deptFilters.length > 0) {
      tree.setCurrentKey(this.deptFilters[0].id);
      this.nodeClick(this.deptFilters[0]);
    }
  }

  /**
   * eltree搜索回调函数
   * @param value
   * @param data
   * @param node
   */
  searchTree(value: any, data: DepartmentInfo, node: any) {
    if (!value) {
      return true;
    }
    return data.name.indexOf(value) !== -1;
  }

  public nodeClick(data: DepartmentInfo) {
    this.deptSelected = data;
    rxevent.publish(EventKeys.deptChange, this.deptSelected);
  }

  add(item?: DepartmentInfo) {
    this.deptEditing = new DepartmentInfo();
    if (item) {
      this.deptSelected = item;
      this.deptEditing.parentId = this.deptSelected.id;
      this.deptEditing.enable = this.deptSelected.enable;
    } else {
      this.deptSelected = new DepartmentInfo();
      this.deptEditing.parentId = '';
    }
    this.showEditDialog = true;
  }

  edit(item: DepartmentInfo) {
    this.deptSelected = item;
    this.deptEditing = _.cloneDeep(item);
    this.showEditDialog = true;
  }

  removeConfirm(item: DepartmentInfo) {
    if (item && !item.enable) {
      return;
    }
    this.deptSelected = item;
    this.$confirm(stringFormatArr(this.$t('RemoveTip').toString(), ['']), this.$t('Tip').toString(), {
      confirmButtonText: this.$t('Comfirm').toString(),
      cancelButtonText: this.$t('Cancel').toString(),
      type: 'warning'
    }).then(() => {
      this.remove(item.id);
    });
  }

  remove(id: string) {
    DeptService.remove(id).then((res: any) => {
      if (res) {
        this.findDepartmentTree();
      }
    });
  }

  save() {
    const form: DeptForm = this.$refs['deptFormRef'] as DeptForm;
    form.saveValid();
  }

  saveSuccess() {
    this.findDepartmentTree();
    this.showEditDialog = false;
  }
}
