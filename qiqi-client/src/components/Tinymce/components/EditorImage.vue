<template>
  <div class="upload-container">
    <el-button
      :style="{ background: color, borderColor: color }"
      icon="el-icon-upload"
      size="mini"
      type="primary"
      @click="dialogVisible = true"
    >
      上传图片
    </el-button>
    <el-dialog :visible.sync="dialogVisible">
      <el-upload
        :multiple="true"
        :file-list="fileList"
        :show-file-list="true"
        :on-change="onUploadChange"
        :on-remove="handleRemove"
        :on-success="handleSuccess"
        :before-upload="beforeUpload"
        class="editor-slide-upload"
        action=""
        :auto-upload="false"
        list-type="picture-card"
      >
        <el-button size="small" type="primary"> 点击上传 </el-button>
      </el-upload>
      <div class="footer">
        <el-button @click="dialogVisible = false"> 取消 </el-button>
        <el-button type="primary" @click="handleSubmit('bg')"> 背景图片 </el-button>
        <el-button type="primary" @click="handleSubmit('pic')"> 普通图片 </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// tslint:disable
import AttachService from '../../../api/attach-service';

export default {
  name: 'EditorSlideUpload',
  props: {
    color: {
      type: String,
      default: '#1890ff'
    }
  },
  data() {
    return {
      dialogVisible: false,
      listObj: {},
      fileList: []
    };
  },
  computed() {
    // dd() {
    //   return AttachService.
    // }
  },
  methods: {
    checkAllSuccess() {
      return Object.keys(this.listObj).every((item) => this.listObj[item].hasSuccess);
    },
    handleSubmit(type) {
      const arr = Object.keys(this.listObj).map((v) => this.listObj[v]);
      if (!this.checkAllSuccess()) {
        this.$message(
          'Please wait for all images to be uploaded successfully. If there is a network problem, please refresh the page and upload again!'
        );
        return;
      }
      if (arr.length > 1 && type === 'bg') {
        this.$message.warning('背景图片只能上传一张');
      }
      const requests = [];
      for (let key in this.listObj) {
        const formData = new FormData();
        formData.append('file', this.listObj[key].raw);
        requests.push(AttachService.uploadSingle(formData));
      }
      Promise.all(requests).then((res) => {
        const arr = res.map((item) => item[0]);
        this.$emit('successCBK', { type, arr });
        this.listObj = {};
        this.fileList = [];
        this.dialogVisible = false;
      });
    },
    onUploadChange(file, fileList) {
      this.listObj[file.uid] = { hasSuccess: true, uid: file.uid, width: this.width, height: this.height, raw: file.raw };
    },
    /**
     * 自动上传成功时候回调
     */
    handleSuccess(response, file) {
      const uid = file.uid;
      const objKeyArr = Object.keys(this.listObj);
      for (let i = 0, len = objKeyArr.length; i < len; i++) {
        if (this.listObj[objKeyArr[i]].uid === uid) {
          this.listObj[objKeyArr[i]].url = AttachService.previewUrl(response.fileName);
          this.listObj[objKeyArr[i]].hasSuccess = true;
          return;
        }
      }
    },
    handleRemove(file) {
      const uid = file.uid;
      const objKeyArr = Object.keys(this.listObj);
      for (let i = 0, len = objKeyArr.length; i < len; i++) {
        if (this.listObj[objKeyArr[i]].uid === uid) {
          delete this.listObj[objKeyArr[i]];
          return;
        }
      }
    },
    /**
     * 自动上传成功时候回调
     */
    beforeUpload(file) {
      const _self = this;
      const _URL = window.URL || window.webkitURL;
      const fileName = file.uid;
      this.listObj[fileName] = {};
      return new Promise((resolve, reject) => {
        const img = new Image();
        img.src = _URL.createObjectURL(file);
        img.onload = function () {
          _self.listObj[fileName] = { hasSuccess: false, uid: file.uid, width: this.width, height: this.height };
        };
        resolve(true);
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.editor-slide-upload {
  margin-bottom: 20px;
  ::v-deep .el-upload--picture-card {
    width: 100%;
  }
}
.footer {
  display: flex;
  justify-content: flex-end;
}
</style>
