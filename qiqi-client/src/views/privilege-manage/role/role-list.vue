<template>
  <div class="role-list">
    <div class="tool-container">
      <el-input :placeholder="$t('SearchTip')" v-model="search"></el-input>
      <el-tooltip effect="dark" :content="$t('Add')" placement="top">
        <el-button type="primary" @click="add">
          <i class="el-icon-plus"></i>
        </el-button>
      </el-tooltip>
      <el-switch v-model="showAll" inactive-text="全部"> </el-switch>
    </div>
    <el-tree
      ref="tree"
      node-key="id"
      default-expand-all
      highlight-current
      :data="deptFilters"
      :props="defaultProps"
      :expand-on-click-node="false"
      :filter-node-method="searchTree"
    >
      <div class="custom-tree-node" slot-scope="{ node, data }" @click="nodeClick(data)">
        <div :title="data.name" class="ifNode">
          <span :class="{ label: true, delete: !data.enable }">{{ data.name }}</span>
        </div>
        <div style="display:none" v-bind:class="{ showBtn: deptSelected.id === data.id }">
          <el-button type="text" @click="add(data)"><i class="el-icon-plus" :title="$t('Add')"></i></el-button>
          <el-button type="text" @click="edit(data)"><i class="el-icon-edit" :title="$t('Edit')"></i></el-button>
          <el-button :disabled="!data.enable" type="text" @click="removeConfirm(data)"
            ><i class="el-icon-delete" :title="$t('Delete')"></i
          ></el-button>
        </div>
      </div>
    </el-tree>
    <!-- 部门add/edit -->
    <el-dialog :title="deptEditing.id ? $t('Edit') : $t('Add')" :visible.sync="showEditDialog" :close-on-click-modal="false">
      <DeptForm
        ref="deptFormRef"
        v-if="showEditDialog"
        :deptInfo.sync="deptEditing"
        :saving.sync="saving"
        @saveSuccess="saveSuccess"
      ></DeptForm>
      <div slot="footer">
        <el-button @click="showEditDialog = false">{{ $t('Cancel') }}</el-button>
        <el-button type="primary" @click="save">{{ $t('Save') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./role-list.ts"></script>
<style lang="scss" scoped src="./role-list.scss"></style>
