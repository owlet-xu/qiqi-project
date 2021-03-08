<template>
  <!-- 图片的宽高比为10:5 -->
  <mt-swipe class="carousel-main" :auto="4000">
    <mt-swipe-item>
      <img class="item" src="../../assets/imgs/carousel-1.png" />
    </mt-swipe-item>
    <mt-swipe-item>
      <img class="item" src="../../assets/imgs/carousel-1.png" />
    </mt-swipe-item>
    <mt-swipe-item>
      <img class="item" src="../../assets/imgs/carousel-1.png" />
    </mt-swipe-item>
    <mt-swipe-item>
      <img class="item" src="../../assets/imgs/carousel-1.png" />
    </mt-swipe-item>
  </mt-swipe>
</template>

<script lang="ts" src="./carousel-main.ts"></script>
<style lang="scss" scoped src="./carousel-main.scss"></style>