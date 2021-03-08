<template>
  <div class="goods-list">
    <carousel-main></carousel-main>
    {{ bottomStatus }}
    <div class="page-loadmore-wrapper" ref="wrapper" :style="{ height: wrapperHeight + 'px' }">
      <mt-loadmore
      ref="loadmore"
      :top-method="loadTop"
      @top-status-change="handleTopChange"
      :bottom-method="loadBottom"
      @bottom-status-change="handleBottomChange"
      bottomDistance='100'
      topDistance='100'
      :auto-fill="false"
    >
      <ul class="page-loadmore-list">
        <li v-for="(item, index) in pageInfo.contents" :key="index" class="page-loadmore-listitem list-container">
          <img :src="item.file1" class="list-img" />
          <div class="list-text">
            <span>{{ item.name + '--' + (++index) }}</span>
            <span>{{ item.price }}</span>
          </div>
        </li>
      </ul>
      <div slot="top" class="mint-loadmore-top">
        <span v-show="topStatus !== 'loading'" :class="{ rotate: topStatus === 'drop' }">↓</span>
        <span v-show="topStatus === 'loading'">Loading...</span>
      </div>
      <div slot="bottom" class="mint-loadmore-bottom">
        <span v-show="bottomStatus !== 'loading'" :class="{ rotate: bottomStatus === 'drop' }">↓</span>
        <span v-show="bottomStatus === 'loading'">Loading...</span>
      </div>
    </mt-loadmore>
    </div>
  </div>
</template>

<script lang="ts" src="./goods-list.ts"></script>
<style lang="scss" scoped src="./goods-list.scss"></style>