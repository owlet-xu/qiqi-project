<template>
  <div class="other-menu-privileges">
    <div class="tool-container">
      <el-input :placeholder="$t('SearchTip')" v-model="search"></el-input>
    </div>
    <el-table
      ref="userTableRef"
      class="user-table"
      :data="listFilter"
      stripe
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column :label="$t('PrivilegeManage.PrivilegeName')" prop="name"></el-table-column>
      <el-table-column :label="$t('PrivilegeManage.PrivilegeCode')" prop="code"></el-table-column>
      <el-table-column :label="$t('CreateTime')" prop="createTime"></el-table-column>
    </el-table>
  </div>
</template>

<script lang="ts" src="./other-menu-privileges.ts"></script>
<style lang="scss" scoped src="./other-menu-privileges.scss"></style>


