import { Vue, Component } from 'vue-property-decorator';
// service
import PrivilegeService from '@/api/privilege-service';
// models
import { PrivilegeInfo } from '@/models/privilege-info';
// components
import PrivilegeForm from './privilege/privilege-form';
// tools
import _ from 'lodash';
import { stringFormatArr } from '@/utils/string-utils';

@Component({
  components: {
    PrivilegeForm
  }
})
export default class PrivilegeList extends Vue {
  private list: PrivilegeInfo[] = [];
  private privilegeSelected: PrivilegeInfo = new PrivilegeInfo();
  private search = '';
  private loadingSave = false;
  private showEditDialog = false;
  private loading = true;
  private showAll = false; // 是否显示所有用户

  get listFilter() {
    return this.list.filter((item: PrivilegeInfo) => {
      if (!this.showAll && !item.enable) {
        return false;
      }
      if (!this.search) {
        return true;
      } else {
        return item.name.indexOf(this.search) !== -1;
      }
    });
  }

  created() {
    this.findAll();
  }

  findAll() {
    PrivilegeService.findAll()
      .then((res: PrivilegeInfo[]) => {
        this.list = res;
      })
      .finally(() => {
        this.loading = false;
      });
  }

  edit(item: PrivilegeInfo) {
    this.privilegeSelected = _.cloneDeep(item);
    this.showEditDialog = true;
  }

  add() {
    this.privilegeSelected = new PrivilegeInfo();
    this.showEditDialog = true;
  }

  save() {
    const form: PrivilegeForm = this.$refs['privilegeFormRef'] as PrivilegeForm;
    form.saveValid();
  }

  saveSuccess() {
    this.showEditDialog = false;
    this.findAll();
  }

  removeConfirm(item: PrivilegeInfo) {
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
    PrivilegeService.remove(id).then((res: any) => {
      if (res) {
        this.findAll();
      }
    });
  }

  disabledRow(data: any) {
    return data.row && data.row.enable ? '' : 'disabled-row';
  }
}
