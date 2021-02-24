<template>
  <div class="goods-add" v-loading="loading" :element-loading-text="$t('LoadingData')">
    <el-form ref="formRef" :model="fromData">
      <el-form-item label="商品名称" prop="name" :rules="rules.userName">
        <el-input v-model="fromData.name"></el-input>
      </el-form-item>
      <el-form-item label="商品价格" prop="price" :rules="rules.name">
        <el-input v-model="fromData.price"></el-input>
      </el-form-item>
      <el-form-item label="商品折扣">
        <el-input v-model="fromData.discount"></el-input>
      </el-form-item>
      <el-form-item label="简单文字描述">
        <el-input v-model="fromData.description"></el-input>
      </el-form-item>
    </el-form>
    <div>
      <el-button>{{ $t('Clear') }}</el-button>
      <el-button type="primary" :loading="loadingSave">{{ $t('Save') }}</el-button>
    </div>
  </div>
</template>

<script lang="ts" src="./goods-add.ts"></script>
<style lang="scss" scoped src="./goods-add.scss"></style>


