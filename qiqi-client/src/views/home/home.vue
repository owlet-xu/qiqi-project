<template>
  <div class="home">
     <div class="browser-header">
      <div class="browser-title">
        <img src="../../assets/logo.png" alt="" class="app-icon" @click="alyzeWeb">
        <span class="el-icon-arrow-left app-go" title="后退"  @click="goBack"></span>&nbsp;
        <span class="el-icon-arrow-right app-go" title="前进" @click="goForward"></span>&nbsp;
        <span class="app-title" v-if="title" :title="title">{{title}}</span>
      </div>
      <div class="browser-search">
        <el-input v-model="searchWorld" size="mini" class="search"></el-input>&nbsp;
        <el-button icon="el-icon-search" circle size="mini" @click="goSearch"></el-button>
      </div>
      <div class="browser-tools">
        <button class="ctrl-icon minimize" title="最小化窗口" @click="handleMinimize">-</button>
        <button class="ctrl-icon maximize" title="最大化窗口" @click="handleMaximize">o</button>
        <button class="ctrl-icon close" title="关闭窗口" @click="handleClose">x</button>
      </div>
    </div>
<el-dialog title="分析结果" :visible.sync="showMessage" width="30%">
  <span></span>
  <span slot="footer" class="dialog-footer">
    <el-button @click="showMessage = false">取 消</el-button>
    <el-button type="primary" @click="showMessage = false">确 定</el-button>
  </span>
</el-dialog>
   <webview ref='myWebView' :src="url" class="browser-body" autosize="on" allowpopups="on"></webview>
  </div>
</template>

<script lang="ts" src="./home.ts"></script>
<style lang="scss" scoped src="./home.scss"></style>


