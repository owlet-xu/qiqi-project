<template>
  <div class="goods-imgs">
    <el-upload
      class="avatar-uploader"
      action=""
      accept="image/jpeg,image/gif,image/png"
      :on-change="onUploadChange1"
      :auto-upload="false"
      :show-file-list="false"
    >
      <img v-if="headImgBase64_1" :src="headImgBase64_1" class="avatar" />
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
    <el-upload
      class="avatar-uploader"
      action=""
      accept="image/jpeg,image/gif,image/png"
      :on-change="onUploadChange2"
      :auto-upload="false"
      :show-file-list="false"
    >
      <img v-if="headImgBase64_2" :src="headImgBase64_2" class="avatar" />
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
  </div>
</template>

<script lang="ts">
import { Vue, Component, Watch } from 'vue-property-decorator';
import { getBase64FromFile } from '@/utils/base64-utils';

@Component
export default class GoodsImgs extends Vue {
  private headImgBase64_1 = '';
  private headImgBase64_2 = '';
  public headImgFile1: any = ''; // 上传的文件
  public headImgFile2: any = ''; // 上传的文件

  private onUploadChange1(file: any, fileList: any) {
    this.onUploadChange(file, 1);
  }

  private onUploadChange2(file: any, fileList: any) {
    this.onUploadChange(file, 2);
  }

  /**
   * 获取上传文件和显示数据
   */
  private onUploadChange(file: any, type: 1 | 2) {
    const isIMAGE = file.raw.type === 'image/jpeg' || file.raw.type === 'image/png' || file.raw.type === 'image/gif';
    const isLt1M = file.size / 1024 / 1024 < 1;
    if (!isIMAGE) {
      this.$message.error('上传文件只能是图片格式!');
      return false;
    }
    if (!isLt1M) {
      this.$message.error('上传文件大小不能超过 1MB!');
      return false;
    }
    const _this: any = this;
    _this['headImgFile' + type] = file.raw;
    getBase64FromFile(file.raw).then((res: any) => {
      _this['headImgBase64_' + type] = res;
    });
  }
}
</script>
<style lang="scss" scoped>
.goods-imgs {
  display: flex;
  .avatar-uploader {
    margin-right: 10px;
  }
  /deep/ .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409eff;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
}
</style>


