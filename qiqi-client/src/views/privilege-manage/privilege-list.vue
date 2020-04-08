<template>
 <div class="privilege-list" v-loading="loading" :element-loading-text="$t('LoadingData')">
    <div class="tool-container">
      <el-input :placeholder="$t('SearchTip')" v-model="search"></el-input>
      <el-tooltip v-if="$privileges($route.name, -1)" effect="dark" :content="$t('Add')" placement="top">
        <el-button type="primary" @click="add">
          <i class="el-icon-plus"></i>
        </el-button>
      </el-tooltip>
      <el-switch v-model="showAll" :inactive-text="$t('All')"> </el-switch>
    </div>
    <el-table class="privilege-table" :data="listFilter" stripe fit highlight-current-row :cell-class-name="disabledRow">
      <el-table-column :label="$t('PrivilegeManage.PrivilegeName')" prop="name"></el-table-column>
      <el-table-column :label="$t('PrivilegeManage.PrivilegeCode')" prop="code"></el-table-column>
      <el-table-column :label="$t('CreateTime')" prop="createTime"></el-table-column>
      <el-table-column :label="$t('Operation')" width="130" align="center">
        <template slot-scope="{ row }">
          <el-tooltip effect="dark" :content="$t('Edit')" placement="top">
            <el-button type="primary" size="mini" @click="edit(row)">
              <i class="el-icon-edit"></i>
            </el-button>
          </el-tooltip>
          <el-tooltip v-if="$privileges($route.name, -1)" effect="dark" :content="$t('Remove')" placement="top">
            <el-button :disabled="row.enable != 1" size="mini" type="danger" @click="removeConfirm(row)"> <i class="el-icon-delete"></i></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :title="privilegeSelected.id ? $t('Edit') : $t('Add')" :visible.sync="showEditDialog" :close-on-click-modal="false">
      <PrivilegeForm ref="privilegeFormRef" v-if="showEditDialog" :privilegeInfo.sync="privilegeSelected" :saving.sync="loadingSave" @saveSuccess="saveSuccess"></PrivilegeForm>
      <div slot="footer">
        <el-button @click="showEditDialog = false">{{ $t('Cancel') }}</el-button>
        <el-button type="primary" @click="save" :loading="loadingSave">{{ $t('Save') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./privilege-list.ts"></script>
<style lang="scss" scoped src="./privilege-list.scss"></style>


