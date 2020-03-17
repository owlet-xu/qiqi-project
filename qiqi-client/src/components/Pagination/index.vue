<template>
  <div
    :class="{'hidden': hidden}"
    class="pagination-container"
  >
    <el-pagination
      :current-page.sync="currentPageTemp"
      :page-size.sync="pageSizeTemp"
      :page-sizes="pageSizes"
      :background="background"
      :layout="layout"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import { scrollTo } from '@/utils/scroll-to';

@Component({
  name: 'Pagination'
})
export default class extends Vue {
  @Prop({ required: true }) private total!: number;
  @Prop({ default: 1 }) private currentPage!: number;
  @Prop({ default: () => [10, 20, 30, 50] }) private pageSizes!: number[];
  @Prop({ default: 20 }) private pageSize!: number;
  @Prop({ default: 'total, sizes, prev, pager, next, jumper' }) private layout!: string;
  @Prop({ default: true }) private background!: boolean;
  @Prop({ default: true }) private autoScroll!: boolean;
  @Prop({ default: false }) private hidden!: boolean;

  get currentPageTemp() {
    return this.currentPage;
  }

  set currentPageTemp(value) {
    this.$emit('update:currentPage', value);
  }

  get pageSizeTemp() {
    return this.pageSize;
  }

  set pageSizeTemp(value) {
    this.$emit('update:pageSize', value);
  }

  handleSizeChange(value: number) {
    this.$emit('pagination', { page: this.currentPage, size: this.pageSize });
    if (this.autoScroll) {
      scrollTo(0, 800);
    }
  }

  handleCurrentChange(value: number) {
    this.$emit('pagination', { page: value, size: this.pageSize });
    if (this.autoScroll) {
      scrollTo(0, 800);
    }
  }
}
</script>

<style lang="scss" scoped>
.pagination-container {
  background: #fff;
  padding: 10px;
  /deep/ .el-pagination {
    float: right;
  }
}

.pagination-container.hidden {
  display: none;
}
</style>
