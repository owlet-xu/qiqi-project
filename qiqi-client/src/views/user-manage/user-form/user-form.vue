<template>
  <div class="user-form">
    <el-form ref="formRef" :model="fromData">
      <el-form-item label="用户名" prop="userName" :rules="rules.userName">
        <el-input v-model="fromData.userName"></el-input>
      </el-form-item>
      <el-form-item label="姓名"  prop="name" :rules="rules.name">
        <el-input v-model="fromData.name"></el-input>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="fromData.mobile"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="fromData.email"></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" src="./user-form.ts"></script>
<style lang="scss" scoped src="./user-form.scss"></style>