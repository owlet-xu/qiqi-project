import { Vue, Component, Watch } from 'vue-property-decorator';
// components
import Pagination from '@/components/Pagination/index.vue';
// services
import GoodsService from '@/api/goods-service';
// models
import { PageInfo } from '@/models/page-info';
import { GoodsInfo } from '@/models/goods/goods-info';
// tools
import _ from 'lodash';
import { stringFormatArr } from '@/utils/string-utils';
import loginService from '@/api/login-service';
import { Path } from '@/router/router-types';

@Component({
  components: {
    Pagination
  }
})
export default class GoodsManage extends Vue {
  private pageInfo: PageInfo<GoodsInfo> = new PageInfo();
  private loading = true;
  private loadingSave = false;
  private goodsInfoSelected = new GoodsInfo();
  private search = '';
  private searchChange: any;
  private showAll = false; // 是否显示所有商品

  created() {
    this.pageInfo.conditions = new GoodsInfo();
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
    this.getGoodsList();
  }

  getGoodsList() {
    this.loading = true;
    GoodsService.findGoodsListPage(this.getPageConditions())
      .then((res: PageInfo<GoodsInfo>) => {
        this.pageInfo.contents = res.contents;
        this.pageInfo.page = res.page + 1;
        this.pageInfo.totalCount = res.totalCount;
        this.pageInfo.size = res.size;
      })
      .finally(() => {
        this.loading = false;
      });
  }

  /**
   * 获取查询条件
   */
  getPageConditions() {
    const condition: PageInfo<GoodsInfo> = new PageInfo();
    condition.page = this.pageInfo.page - 1;
    condition.size = this.pageInfo.size;
    condition.conditions = this.pageInfo.conditions;
    condition.search = this.search;
    return condition;
  }

  edit(item: GoodsInfo) {
    this.goodsInfoSelected = _.cloneDeep(item);
    this.$router.push({
      path: Path.GoodsManageAdd,
      query: {
        id: item.id
      }
    });
  }

  add() {
    this.goodsInfoSelected = new GoodsInfo();
    this.$router.push({
      path: Path.GoodsManageAdd,
      query: {
        id: ''
      }
    });
  }

  save() {
    // const form: UserForm = this.$refs['userFormRef'] as UserForm;
    // form.saveValid();
  }

  saveSuccess() {
    this.getUserListFirstPage();
  }

  removeConfirm(item: GoodsInfo) {
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
    GoodsService.disableGoods(id).then((res: any) => {
      if (res) {
        this.getGoodsList();
      }
    });
  }

  // 分页改变
  pagination(data: any) {
    this.getGoodsList();
  }

  disabledRow(data: any) {
    return data.row && data.row.enable ? '' : 'disabled-row';
  }
}
