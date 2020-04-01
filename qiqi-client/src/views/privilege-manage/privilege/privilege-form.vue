<template>
  <div class="privilege-form">
    <el-form ref="formRef" :model="fromData">
      <el-form-item label="权限名称" prop="name" :rules="rules.name">
        <el-input v-model="fromData.name"></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" src="./privilege-form.ts"></script>
<style lang="scss" scoped src="./privilege-form.scss"></style>