<template>
  <div class="menu-info">
    <div class="menu-info-container">
      <div class="tool-container">
        <span class="common-title">{{$t('PrivilegeManage.MenuInfo')}}</span>
      </div>
      <div class="info" v-if="currMenuInfo.id">
        <span class="item">{{ currMenuInfo.name }}</span>
        <span class="item">{{ currMenuInfo.code }}</span>
        <span class="item">{{ currMenuInfo.url }}</span>
      </div>
    </div>
    <div class="privilige-list-container">
      <div class="tool-container">
        <span class="common-title">{{$t('PrivilegeManage.PrivilegeList')}}</span>
        <el-input :placeholder="$t('SearchTip')" v-model="search"></el-input>
        <el-tooltip effect="dark" :content="$t('Add')" placement="top">
          <el-button type="primary" @click="add">
            <i class="el-icon-plus"></i>
          </el-button>
        </el-tooltip>
        <el-tooltip effect="dark" :content="$t('Remove')" placement="top">
          <el-button type="danger" @click="removeConfirm">
            <i class="el-icon-delete"></i>
          </el-button>
        </el-tooltip>
      </div>
      <el-table class="user-table" style="width: 100%" :data="listFilter" stripe highlight-current-row @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column :label="$t('PrivilegeManage.PrivilegeName')" prop="name"></el-table-column>
        <el-table-column :label="$t('PrivilegeManage.PrivilegeCode')" prop="code"></el-table-column>
        <el-table-column :label="$t('CreateTime')" prop="createTime"></el-table-column>
      </el-table>
    </div>
    <!-- 添加权限dialog -->
    <el-dialog :title="$t('PrivilegeManage.AddPrivilege')" :visible.sync="showEditDialog" :close-on-click-modal="false">
      <OtherMenuPrivileges ref="privilegeFormRef" v-if="showEditDialog" :menuId="currMenuInfo.id" @saveSuccess="saveSuccess"></OtherMenuPrivileges>
      <div slot="footer">
        <el-button @click="showEditDialog = false">{{ $t('Cancel') }}</el-button>
        <el-button type="primary" @click="savePrivileges">{{ $t('Add') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./menu-infos.ts"></script>
<style lang="scss" scoped src="./menu-infos.scss"></style>


