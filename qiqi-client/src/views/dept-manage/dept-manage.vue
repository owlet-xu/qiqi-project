<template>
  <div class="dept-manage">
    <div :class="['dept-tree-container', isMobile ? 'dept-tree-mobile': '']">
      <DeptTree></DeptTree>
    </div>
    <div :class="['dept-users-container', isMobile ? 'dept-users-mobile': '']">
      <DeptUsers></DeptUsers>
    </div>
  </div>
</template>

<script lang="ts" src="./dept-manage.ts"></script>
<style lang="scss" scoped src="./dept-manage.scss"></style>


