<template>
  <div class="user-manage" v-loading="loading" :element-loading-text="$t('LoadingData')">
    <div class="tool-container">
      <el-input :placeholder="$t('SearchTip')" v-model="search" @input="searchChange"></el-input>
      <el-tooltip effect="dark" :content="$t('Add')" placement="top">
        <el-button type="primary" @click="add">
          <i class="el-icon-plus"></i>
        </el-button>
      </el-tooltip>
      <el-switch v-model="showAll" :inactive-text="$t('All')"> </el-switch>
    </div>
    <el-table class="user-table" :data="pageInfo.contents" stripe fit highlight-current-row :cell-class-name="disabledRow">
      <el-table-column :label="$t('UserManage.UserName')" prop="userName"></el-table-column>
      <el-table-column :label="$t('UserManage.Name')" prop="name"></el-table-column>
      <el-table-column :label="$t('UserManage.Mobile')" prop="mobile"></el-table-column>
      <el-table-column :label="$t('CreateTime')" prop="createTime"></el-table-column>
      <el-table-column :label="$t('Operation')" width="200" align="center">
        <template slot-scope="{ row }">
          <el-tooltip effect="dark" :content="$t('Edit')" placement="top">
            <el-button type="primary" size="mini" @click="edit(row)">
              <i class="el-icon-edit"></i>
            </el-button>
          </el-tooltip>
          <el-tooltip effect="dark" :content="$t('Login.ChangePassword')" placement="top">
            <el-button type="primary" size="mini" @click="editPassword(row)">
              <i class="el-icon-lock"></i>
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
    <el-dialog :title="userInfoSelected.id ? $t('Edit') : $t('Add')" :visible.sync="showEditDialog" :close-on-click-modal="false">
      <UserForm
        ref="userFormRef"
        v-if="showEditDialog"
        :userInfo.sync="userInfoSelected"
        :saving.sync="loadingSave"
        @saveSuccess="saveSuccess"
      ></UserForm>
      <div slot="footer">
        <el-button @click="showEditDialog = false">{{ $t('Cancel') }}</el-button>
        <el-button type="primary" @click="save" :loading="loadingSave">{{ $t('Save') }}</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="$t('Login.ChangePassword')" :visible.sync="showPasswordDialog" :close-on-click-modal="false">
      <PasswordForm
        ref="passwordFormRef"
        v-if="showPasswordDialog"
        :userInfo.sync="userInfoSelected"
        :saving.sync="loadingSave"
        @saveSuccess="savePasswordSuccess"
      ></PasswordForm>
      <div slot="footer">
        <el-button @click="showPasswordDialog = false">{{ $t('Cancel') }}</el-button>
        <el-button type="warning" @click="resetPassword" :loading="loadingSave">{{ $t('Login.ResetPassword') }}</el-button>
        <el-button type="primary" @click="savePassword" :loading="loadingSave">{{ $t('Save') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./user-manage.ts"></script>
<style lang="scss" scoped src="./user-manage.scss"></style>


