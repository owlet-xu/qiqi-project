<template>
  <div class="goods-manage" v-loading="loading" :element-loading-text="$t('LoadingData')">
    <div class="tool-container">
      <el-input :placeholder="$t('SearchTip')" v-model="search" @input="searchChange"></el-input>
      <el-tooltip effect="dark" :content="$t('Add')" placement="top">
        <el-button type="primary" @click="add">
          <i class="el-icon-plus"></i>
        </el-button>
      </el-tooltip>
      <el-switch v-model="showAll" :inactive-text="$t('All')"> </el-switch>
    </div>
    <el-table class="goods-table" :data="pageInfo.contents" stripe fit highlight-current-row :cell-class-name="disabledRow">
      <el-table-column :label="$t('Goods.Name')" prop="name"></el-table-column>
      <el-table-column :label="$t('Goods.Price')" prop="price"></el-table-column>
      <el-table-column :label="$t('Goods.Description')" prop="description"></el-table-column>
      <el-table-column :label="$t('CreateTime')" prop="createTime"></el-table-column>
      <el-table-column :label="$t('Operation')" width="200" align="center">
        <template slot-scope="{ row }">
          <el-tooltip effect="dark" :content="$t('Edit')" placement="top">
            <el-button type="primary" size="mini" @click="edit(row)">
              <i class="el-icon-edit"></i>
            </el-button>
          </el-tooltip>
          <el-tooltip effect="dark" :content="$t('Remove')" placement="top">
            <el-button :disabled="row.enable != 1" size="mini" type="danger" @click="removeConfirm(row)">
              <i class="el-icon-delete"></i
            ></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <Pagination
      :total="pageInfo.totalCount"
      :currentPage.sync="pageInfo.page"
      :pageSize.sync="pageInfo.size"
      :pageSizes="pageInfo.sizes"
      @pagination="pagination"
    ></Pagination>
  </div>
</template>

<script lang="ts" src="./goods-manage.ts"></script>
<style lang="scss" scoped src="./goods-manage.scss"></style>


