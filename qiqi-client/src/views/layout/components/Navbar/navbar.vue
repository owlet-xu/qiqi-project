<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />
    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" />
    <div class="right-menu">
      <template v-if="device !== 'mobile'">
        <!-- 不需要搜索 -->
        <!-- <header-search class="right-menu-item" /> -->
        <error-log class="errLog-container right-menu-item hover-effect" />
        <screenfull class="right-menu-item hover-effect" />
        <lang-select class="right-menu-item hover-effect" />
      </template>
      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar" class="user-avatar" />
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/">
            <el-dropdown-item>
              {{ $t('Router.Home') }}
            </el-dropdown-item>
          </router-link>
          <el-dropdown-item @click.native="editPassword">
            <span style="display:block;">
              {{ $t('Login.ChangePassword') }}
            </span>
          </el-dropdown-item>
          <el-dropdown-item @click.native="edit">
            <span style="display:block;">
              {{ $t('Navbar.EditSelfInfo') }}
            </span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="LogOut">
            <span style="display:block;">
              {{ $t('Navbar.LoginOut') }}
            </span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <!-- 个人信息修改 -->
    <el-dialog :title="$t('Edit')" :visible.sync="showEditDialog" :close-on-click-modal="false" :append-to-body="true">
      <UserForm
        ref="navBarUserFormRef"
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
    <!-- 个人密码修改 -->
    <el-dialog :title="$t('Login.ChangePassword')" :visible.sync="showPasswordDialog" :close-on-click-modal="false" :append-to-body="true">
      <PasswordForm
        ref="navBarPasswordFormRef"
        v-if="showPasswordDialog"
        :userInfo.sync="userInfoSelected"
        :saving.sync="loadingSave"
        @saveSuccess="savePasswordSuccess"
      ></PasswordForm>
      <div slot="footer">
        <el-button @click="showPasswordDialog = false">{{ $t('Cancel') }}</el-button>
        <el-button type="primary" @click="savePassword" :loading="loadingSave">{{ $t('Save') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./navbar.ts"></script>
<style lang="scss" scoped src="./navbar.scss"></style>
