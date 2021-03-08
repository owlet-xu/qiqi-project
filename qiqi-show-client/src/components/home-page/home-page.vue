<template>
  <div class="home-page">
    <carousel-main></carousel-main>
    <div class="menu-container">
      <div class="menu-row">
        <div class="menu-item no-right-border" @click="handleMenu">
          <img src="../../assets/imgs/menu1.png" />
          <span>移动商城</span>
        </div>
        <div class="menu-item no-right-border" @click="handleMenu">
          <img src="../../assets/imgs/menu1.png" />
          <span>移动商城</span>
        </div>
        <div class="menu-item no-right-border" @click="handleMenu">
          <img src="../../assets/imgs/menu1.png" />
          <span>移动商城</span>
        </div>
        <div class="menu-item">
          <img src="../../assets/imgs/menu1.png" />
          <span>移动商城</span>
        </div>
      </div>
      <div class="menu-row">
        <div class="menu-item no-right-border no-top-border" @click="handleMenu">
          <img src="../../assets/imgs/menu1.png" />
          <span>移动商城</span>
        </div>
        <div class="menu-item no-right-border no-top-border" @click="handleMenu">
          <img src="../../assets/imgs/menu1.png" />
          <span>移动商城</span>
        </div>
        <div class="menu-item no-right-border no-top-border" @click="handleMenu">
          <img src="../../assets/imgs/menu1.png" />
          <span>移动商城</span>
        </div>
        <div class="menu-item no-top-border">
          <img src="../../assets/imgs/menu1.png" />
          <span>移动商城</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./home-page.ts"></script>
<style lang="scss" scoped src="./home-page.scss"></style>