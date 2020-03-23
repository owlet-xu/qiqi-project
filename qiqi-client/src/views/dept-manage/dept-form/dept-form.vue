<template>
  <div class="dept-form">
    <el-form ref="formRef" :model="fromData">
      <el-form-item label="部门名称" prop="name" :rules="rules.name">
        <el-input v-model="fromData.name"></el-input>
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

<script lang="ts" src="./dept-form.ts"></script>
<style lang="scss" scoped src="./dept-form.scss"></style>