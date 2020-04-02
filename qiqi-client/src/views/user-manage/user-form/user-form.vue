<template>
  <div class="user-form">
    <el-form ref="formRef" :model="fromData">
      <el-form-item label="用户名" prop="userName" :rules="rules.userName">
        <el-input v-model="fromData.userName"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="name" :rules="rules.name">
        <el-input v-model="fromData.name"></el-input>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="fromData.mobile"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="fromData.email"></el-input>
      </el-form-item>
      <el-form-item label="部门">
        <el-select style="width: 100%" v-model="userInfo.deptInfos" placeholder="请选择部门" multiple filterable value-key="id">
          <el-option v-for="(item, index) in depts" :key="index" :value="item" :label="item.name"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="角色">
        <el-select style="width: 100%" v-model="userInfo.roleInfos" placeholder="请选择角色" multiple filterable value-key="id">
          <el-option v-for="(item, index) in roles" :key="index" :value="item" :label="item.name"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-upload
          class="avatar-uploader"
          action=""
          accept="image/jpeg,image/gif,image/png"
          :on-change="onUploadChange"
          :auto-upload="false"
          :show-file-list="false"
        >
          <img v-if="headImgBase64" :src="headImgBase64" class="avatar" />
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" src="./user-form.ts"></script>
<style lang="scss" scoped src="./user-form.scss"></style>