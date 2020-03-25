<template>
  <div class="dept-users">
    <div class="dept-info-container">
      <div class="tool-container">
        <span class="common-title">部门信息</span>
      </div>
      <div class="dept-info" v-if="currDeptInfo.id">
        <img v-if="currDeptInfo.imgId" :src="getDeptImgUrl(currDeptInfo.imgId)" class="item avatar" />
        <span v-else class="item no-avatar"></span>
        <span class="item">{{ currDeptInfo.name }}</span>
      </div>
    </div>
    <div class="user-list-container">
      <div class="tool-container">
        <span class="common-title">人员列表</span>
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
      <el-table class="user-table" style="width: 100%" :data="pageInfo.contents" stripe highlight-current-row :row-key="getRowKeys" @selection-change="handleSelectionChange">
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
    <!-- 添加用户dialog -->
    <el-dialog :title="$t('Edit')" :visible.sync="showEditDialog" :close-on-click-modal="false">
      <OtherDeptUsers ref="userAddRef" v-if="showEditDialog" :deptId="currDeptInfo.id"></OtherDeptUsers>
      <div slot="footer">
        <el-button @click="showEditDialog = false">{{ $t('Cancel') }}</el-button>
        <el-button type="primary" @click="addUsers">{{ $t('Save') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./dept-users.ts"></script>
<style lang="scss" scoped src="./dept-users.scss"></style>


