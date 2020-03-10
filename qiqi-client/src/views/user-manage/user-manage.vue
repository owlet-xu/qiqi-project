<template>
  <div
    class="user-manage"
    v-loading="loading"
    :element-loading-text="$t('LoadingData')"
  >
    <div class="filter-container">
      <el-input></el-input>
    </div>
    <el-table
      class="user-table"
      :data="pageInfo.contents"
      border
      fit
      highlight-current-row
    >
      <el-table-column
        :label="$t('用户名')"
        prop='userName'
      ></el-table-column>
      <el-table-column
        :label="$t('姓名')"
        prop='name'
      ></el-table-column>
      <el-table-column
        :label="$t('手机')"
        prop='mobile'
      ></el-table-column>
      <el-table-column
        :label="$t('创建时间')"
        prop='createTime'
      ></el-table-column>
      <el-table-column :label="$t('创建时间')">
        <template slot-scope="{row}">
          <el-button
            type="primary"
            size="mini"
          >
            {{ $t('table.edit') }}
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleModifyStatus(row,'deleted')"
          >
            {{ $t('table.delete') }}
          </el-button>
        </template></el-table-column>
    </el-table>
    <Pagination
      v-show="pageInfo.totalCount > 0"
      :total="pageInfo.totalCount"
      :page.sync="pageInfo.page"
      :limit.sync="pageInfo.size"
      @pagination="getUserList"
    ></pagination>
  </div>
</template>

<script lang="ts" src="./user-manage.ts"></script>
<style lang="scss" scoped src="./user-manage.scss"></style>


