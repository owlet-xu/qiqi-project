<template>
  <div class="menu-form">
    <el-form ref="formRef" :model="fromData">
      <el-form-item label="菜单名称" prop="name" :rules="rules.name">
        <el-input v-model="fromData.name"></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" src="./menu-form.ts"></script>
<style lang="scss" scoped src="./menu-form.scss"></style>