<template>
  <div class="other-dept-users">
    <div class="tool-container">
      <el-input :placeholder="$t('SearchTip')" v-model="search"></el-input>
    </div>
    <el-table ref="userTableRef" class="user-table" :data="pageInfo.contents" stripe fit highlight-current-row @selection-change="handleSelectionChange" :row-key="getRowKeys">
      <el-table-column type="selection" width="55" :reserve-selection="true"> </el-table-column>
      <el-table-column :label="$t('UserManage.UserName')" prop="userName"></el-table-column>
      <el-table-column :label="$t('UserManage.Name')" prop="name"></el-table-column>
      <el-table-column :label="$t('UserManage.Mobile')" prop="mobile"></el-table-column>
      <el-table-column :label="$t('CreateTime')" prop="createTime"></el-table-column>
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

<script lang="ts" src="./other-dept-users.ts"></script>
<style lang="scss" scoped src="./other-dept-users.scss"></style>


