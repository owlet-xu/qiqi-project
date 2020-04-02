<template>
  <div class="role-menu-tree" v-loading="loading">
    <div class="role-info-container">
      <div class="tool-container">
        <span class="common-title">角色信息</span>
      </div>
      <div class="role-info" v-if="currRoleInfo.id">
        <span class="item">{{ currRoleInfo.name }}</span>
      </div>
    </div>
    <div class="tree-container">
      <div class="tool-container">
        <span class="common-title">菜单权限树</span>
        <el-input :placeholder="$t('SearchTip')" v-model="search"></el-input>
        <el-tooltip effect="dark" :content="$t('Edit')" placement="top">
          <el-button type="primary" @click="edit">
            <i class="el-icon-edit"></i>
          </el-button>
        </el-tooltip>
        <el-tooltip effect="dark" :content="$t('Save')" placement="top">
          <el-button type="primary" @click="saveConfirm" :disabled="!canEdit">
            <i class="el-icon-check"></i>
          </el-button>
        </el-tooltip>
      </div>
      <el-tree
        ref="tree"
        node-key="id"
        default-expand-all
        highlight-current
        show-checkbox
        :data="menuTree"
        :props="defaultProps"
        :expand-on-click-node="false"
        :filter-node-method="searchTree"
        :check-strictly="!canEdit"
      >
        <div class="custom-tree-node" slot-scope="{ node, data }">
          <div :title="data.name" :class="{ ifNode: true, ifPrivilege: !!data.isPrivilege }">
            <span :class="{ label: true, delete: !data.enable }">{{ data.name }}</span>
          </div>
        </div>
      </el-tree>
    </div>
  </div>
</template>
<script lang="ts" src="./role-menu-tree.ts"></script>
<style lang="scss" scoped src="./role-menu-tree.scss"></style>