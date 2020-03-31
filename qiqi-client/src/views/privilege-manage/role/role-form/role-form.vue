<template>
  <div class="dept-form">
    <el-form ref="formRef" :model="fromData">
      <el-form-item label="角色名称" prop="name" :rules="rules.name">
        <el-input v-model="fromData.name"></el-input>
      </el-form-item>
      <el-form-item label="code标识" prop="code" >
        <el-input :disabled="true" v-model="fromData.code"></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" src="./role-form.ts"></script>
<style lang="scss" scoped src="./role-form.scss"></style>