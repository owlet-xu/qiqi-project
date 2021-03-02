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
    <el-upload
      class="avatar-uploader"
      action=""
      accept="image/jpeg,image/gif,image/png"
      :on-change="onUploadChange3"
      :auto-upload="false"
      :show-file-list="false"
    >
      <img v-if="headImgBase64[2]" :src="headImgBase64[2]" class="avatar" />
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
    <el-upload
      class="avatar-uploader"
      action=""
      accept="image/jpeg,image/gif,image/png"
      :on-change="onUploadChange4"
      :auto-upload="false"
      :show-file-list="false"
    >
      <img v-if="headImgBase64[3]" :src="headImgBase64[3]" class="avatar" />
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
    <el-upload
      class="avatar-uploader"
      action=""
      accept="image/jpeg,image/gif,image/png"
      :on-change="onUploadChange5"
      :auto-upload="false"
      :show-file-list="false"
    >
      <img v-if="headImgBase64[4]" :src="headImgBase64[4]" class="avatar" />
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
    <!-- 视频上传 -->
    <el-upload
      class="avatar-uploader"
      action=""
      accept="video/mp4,video/avi,video/flv"
      :on-change="onUploadChange6"
      :auto-upload="false"
      :show-file-list="false"
    >
      <div v-if="headImgBase64[5]">
        <video :src="headImgBase64[5]" class="avatar"></video>
        <span class="el-upload-list__item-actions">
          <span v-if="!disabled" class="el-upload-list__item-delete">
            <i class="el-icon-delete"></i>
          </span>
        </span>
      </div>
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
  </div>
</template>

<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator';
import { getBase64FromFile } from '@/utils/base64-utils';
import AttachService from '@/api/attach-service';

@Component
export default class GoodsImgs extends Vue {
  @Prop({ default: ['', '', '', '', '', ''] })
  imgUrls!: string[];
  public headImgFile: any = [null, null, null, null, null, null]; // 上传的文件

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

  private onUploadChange3(file: any, fileList: any) {
    this.onUploadChange(file, 2);
  }

  private onUploadChange4(file: any, fileList: any) {
    this.onUploadChange(file, 3);
  }

  private onUploadChange5(file: any, fileList: any) {
    this.onUploadChange(file, 4);
  }

  private onUploadChange6(file: any, fileList: any) {
    this.onUploadChange(file, 5);
  }

  /**
   * 获取上传文件和显示数据
   */
  private onUploadChange(file: any, index: 0 | 1 | 2 | 3 | 4 | 5) {
    index === 5 ? this.onUploadVideo(file, index) : this.onUploadImg(file, index);
  }
  onUploadImg(file: any, index: 0 | 1 | 2 | 3 | 4 | 5) {
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
  onUploadVideo(file: any, index: 0 | 1 | 2 | 3 | 4 | 5) {
    const isIMAGE = file.raw.type === 'video/mp4' || file.raw.type === 'video/flv' || file.raw.type === 'video/avi';
    const isLt1M = file.size / 1024 / 1024 < 50;
    if (!isIMAGE) {
      this.$message.error('上传文件只能是视频格式!');
      return false;
    }
    if (!isLt1M) {
      this.$message.error('上传文件大小不能超过 50MB!');
      return false;
    }
    this.headImgFile[index] = file.raw;
    this.headImgBase64[index] = AttachService.previewVideoUrl(file.raw);
    this.$forceUpdate();
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


