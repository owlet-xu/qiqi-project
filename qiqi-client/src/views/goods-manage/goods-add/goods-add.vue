<template>
  <div class="goods-add" v-loading="loading" :element-loading-text="$t('LoadingData')">
    <el-form ref="formRef" :model="fromData">
      <div class="form-item-row">
        <goods-imgs ref="goodsImgsRef" :imgUrls="[fromData.pic1, fromData.pic2]"></goods-imgs>
        <div class="form-item-row-item">
          <div class="form-item-row">
            <el-form-item :label="$t('Goods.Name')" prop="name" :rules="rules.name">
              <el-input v-model="fromData.name"></el-input>
            </el-form-item>
            <el-form-item :label="$t('Goods.Price')" prop="price" :rules="rules.price">
              <el-input v-model="fromData.price"></el-input>
            </el-form-item>
            <el-form-item :label="$t('Goods.Discount')" prop="discount" :rules="rules.discount">
              <el-input v-model="fromData.discount"></el-input>
            </el-form-item>
          </div>
          <div class="form-item-row">
            <el-form-item :label="$t('类型1')" prop="type1">
              <el-select style="width: 100%" v-model="fromData.type1" placeholder="类型1" filterable>
                <el-option :value="1" :label="'a'"> </el-option>
                <el-option :value="2" :label="'b'"> </el-option>
                <el-option :value="3" :label="'c'"> </el-option>
                <el-option :value="4" :label="'d'"> </el-option>
              </el-select>
            </el-form-item>
            <el-form-item :label="$t('类型2')" prop="type2">
              <el-select style="width: 100%" v-model="fromData.type2" placeholder="类型2" filterable>
                <el-option :value="1" :label="'a'"> </el-option>
                <el-option :value="2" :label="'b'"> </el-option>
                <el-option :value="3" :label="'c'"> </el-option>
                <el-option :value="4" :label="'d'"> </el-option>
              </el-select>
            </el-form-item>
           <el-form-item :label="$t('启用禁用')" prop="enable" :rules="rules.enable">
              <el-select style="width: 100%" v-model="fromData.enable">
                <el-option :value="1" :label="'启用'"> </el-option>
                <el-option :value="0" :label="'禁用'"> </el-option>
              </el-select>
            </el-form-item>
          </div>
        </div>
      </div>
      <div class="form-item-row"></div>
      <el-form-item label="简单文字描述">
        <el-input v-model="fromData.description" type="textarea" maxlength="30" show-word-limit rows="4"></el-input>
      </el-form-item>
    </el-form>
    <div>
      <div class="form-title">商品详情</div>
      <tinymce ref="tinymce" v-model="fromData.detail" :height="450" />
    </div>
    <div class="footer">
      <el-button @click="clear">{{ $t('Clear') }}</el-button>
      <el-button @click="setBackgroudImg">{{ $t('setBackgroudImg') }}</el-button>
      <el-button @click="saveValidate" type="primary">{{ $t('Save') }}</el-button>
    </div>
  </div>
</template>

<script lang="ts" src="./goods-add.ts"></script>
<style lang="scss" scoped src="./goods-add.scss"></style>


