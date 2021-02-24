<template>
  <div class="goods-add" v-loading="loading" :element-loading-text="$t('LoadingData')">
    <el-form ref="formRef" :model="fromData">
      <el-form-item :label="$t('Goods.Name')" prop="name" :rules="rules.name">
        <el-input v-model="fromData.name"></el-input>
      </el-form-item>
      <div class="form-item-row">
        <el-form-item :label="$t('Goods.Price')" prop="price" :rules="rules.price">
          <el-input v-model="fromData.price"></el-input>
        </el-form-item>
        <el-form-item :label="$t('Goods.Discount')" prop="discount" :rules="rules.discount">
          <el-input v-model="fromData.discount"></el-input>
        </el-form-item>
      </div>
      <el-form-item label="简单文字描述">
        <el-input v-model="fromData.description" type="textarea" maxlength="30" show-word-limit rows="4"></el-input>
      </el-form-item>
    </el-form>
    <div>
      <div class="form-title">商品详情</div>
      <tinymce ref="tinymce" v-model="fromData.detail" :height="700" />
    </div>
    <div class="footer">
      <el-button @click="clear">{{ $t('Clear') }}</el-button>
      <el-button @click="saveValidate" type="primary">{{ $t('Save') }}</el-button>
    </div>
  </div>
</template>

<script lang="ts" src="./goods-add.ts"></script>
<style lang="scss" scoped src="./goods-add.scss"></style>


