import { Vue, Component } from 'vue-property-decorator';
import { AppModule } from '@/store/modules/app';
// models
import { PageInfo } from '@/models/page-info';
import { GoodsInfo } from '@/models/goods/goods-info';
// services
import GoodsService from '@/api/goods-service';
// componets
import CarouselMain from '../carousel-main/carousel-main.vue';

@Component({
  components: {
    CarouselMain
  }
})
export default class GoodsList extends Vue {
  private pageInfo: PageInfo<GoodsInfo> = new PageInfo();
  topStatus: '' | 'pull' | 'drop' | 'loading' = '';
  bottomStatus: '' | 'pull' | 'drop' | 'loading' = '';
  wrapperHeight = 0; // 高度计算

  created() {
    this.pageInfo.conditions = new GoodsInfo();
    this.getGoodsListFirstPage();
  }

  mounted() {
    this.wrapperHeight = document.documentElement.clientHeight - (this.$refs.wrapper as any).getBoundingClientRect().top;
  }

  getGoodsListFirstPage() {
    this.pageInfo.page = 1;
    this.getGoodsList();
  }

  handleTopChange(status: any) {
    this.topStatus = status;
  }

  /**
   * 组件已被释放，top-method 正在执行
   * @param id
   */
  loadTop(id: any) {
    this.getGoodsListFirstPage();
  }

  handleBottomChange(status: any) {
    this.bottomStatus = status;
  }

  loadBottom() {
    if (this.pageInfo.contents.length >= this.pageInfo.totalCount) {
      (this.$refs['loadmore'] as any).onBottomLoaded();
      this.bottomStatus = '';
      this.$toast({
        message: `已加载全部数据`,
        position: 'bottom',
        duration: 1000
      });
      return;
    }
    this.pageInfo.page = ++this.pageInfo.page;
    this.getGoodsList();
  }

  getGoodsList() {
    GoodsService.findGoodsListPage(this.getPageConditions())
      .then((res: PageInfo<GoodsInfo>) => {
        if (this.topStatus === 'loading' || !this.pageInfo.contents.length) {
          this.pageInfo.contents = res.contents;
        } else if (this.bottomStatus === 'loading') {
          this.pageInfo.contents = [...this.pageInfo.contents, ...res.contents];
        }
        this.pageInfo.page = res.page + 1;
        this.pageInfo.totalCount = res.totalCount;
        this.pageInfo.size = res.size;
      })
      .finally(() => {
        if (this.topStatus === 'loading') {
          (this.$refs['loadmore'] as any).onTopLoaded();
        } else if (this.bottomStatus === 'loading') {
          (this.$refs['loadmore'] as any).onBottomLoaded();
          this.bottomStatus = '';
        }
      });
  }

  /**
   * 获取查询条件
   * @returns
   */
  getPageConditions() {
    const condition: PageInfo<GoodsInfo> = new PageInfo();
    condition.page = this.pageInfo.page - 1;
    condition.size = this.pageInfo.size;
    condition.conditions = this.pageInfo.conditions;
    // condition.search = this.search;
    return condition;
  }
}
