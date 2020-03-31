<template>
  <div class="role-manage">
    <div :class="['list-container', isMobile ? 'list-container-mobile' : '']">
      <RoleList></RoleList>
    </div>
    <div :class="['menu-tree-container', isMobile ? 'menu-tree-container-mobile' : '']">
      <RoleMenuTree></RoleMenuTree>
    </div>
  </div>
</template>

<script lang="ts" src="./role-manage.ts"></script>
<style lang="scss" scoped src="./role-manage.scss"></style>


