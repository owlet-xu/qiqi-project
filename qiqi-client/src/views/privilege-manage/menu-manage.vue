<template>
  <div class="menu-manage">
    <div :class="['tree-container', isMobile ? 'tree-container-mobile' : '']">
     <MenuTree></MenuTree>
    </div>
    <div :class="['info-container', isMobile ? 'info-container-mobile' : '']">
      <MenuInfos></MenuInfos>
    </div>
  </div>
</template>

<script lang="ts" src="./menu-manage.ts"></script>
<style lang="scss" scoped src="./menu-manage.scss"></style>


