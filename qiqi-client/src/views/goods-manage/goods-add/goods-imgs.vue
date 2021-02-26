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
      <img v-if="headImgBase64[0]" :src="headImgBase64[0]" class="avatar" />
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
      <img v-if="headImgBase64[1]" :src="headImgBase64[1]" class="avatar" />
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
  </div>
</template>

<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator';
import { getBase64FromFile } from '@/utils/base64-utils';

@Component
export default class GoodsImgs extends Vue {
  @Prop({ default: ['', ''] })
  imgUrls!: string[];
  public headImgFile: any = [{}, {}]; // 上传的文件

  get headImgBase64() {
      return this.imgUrls;
  }

  set headImgBase64(val: any) {
      this.$emit('update:imgUrls', val);
  }

  private onUploadChange1(file: any, fileList: any) {
    this.onUploadChange(file, 0);
  }

  private onUploadChange2(file: any, fileList: any) {
    this.onUploadChange(file, 1);
  }

  /**
   * 获取上传文件和显示数据
   */
  private onUploadChange(file: any, index: 0 | 1) {
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
    this.headImgFile[index] = file.raw;
    getBase64FromFile(file.raw).then((res: any) => {
      this.headImgBase64[index] = res;
      this.$forceUpdate();
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


