import { Vue, Component, Prop } from 'vue-property-decorator';
import { UserInfo } from '@/models/user-info';
import { Form as ElForm } from 'element-ui';
import AttachService from '@/api/attach-service';
// tools
import { stringFormatArr } from '@/utils/string-utils';
import { getBase64FromFile } from '@/utils/base64-utils';

@Component
export default class UserForm extends Vue {
  @Prop()
  userInfo!: UserInfo;

  private headImgBase64: any = '';
  private headImgFile: any = '';

  private rules = {
    userName: [{ required: true, validator: this.validateUserName, trigger: ['blur', 'change'] }],
    name: [{ required: true, validator: this.validateName, trigger: ['blur', 'change'] }]
  };

  get fromData() {
    return this.userInfo;
  }

  created() {
    if (this.userInfo.headImg) {
      this.headImgBase64 = AttachService.previewUrl(this.userInfo.headImg);
      // AttachService.preview(this.userInfo.headImg).then((res: any) => {
      //   debugger;
      //  this.headImgBase64 = res;
      // });
    }

  }

  /**
   * 验证表单-暴漏给父组件
   */
  async validForm(): Promise<boolean> {
    const form: ElForm = this.$refs['formRef'] as ElForm;
    return form.validate();
  }

  /**
   * 获取图片数据
   */
  getHeadImgFile() {
    return this.headImgFile;
  }

  private validateUserName(rule: any, value: any, callback: any) {
    if (!value || value.length === 0) {
      callback(new Error(stringFormatArr(this.$t('NotEmptyTip').toString(), [this.$t('UserManage.UserName').toString()])));
    } else {
      callback();
    }
  }

  private validateName(rule: any, value: any, callback: any) {
    if (!value || value.length === 0) {
      callback(new Error(stringFormatArr(this.$t('NotEmptyTip').toString(), [this.$t('UserManage.Name').toString()])));
    } else {
      callback();
    }
  }

  private onUploadChange(file: any, fileList: any) {
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
    this.headImgFile = file.raw;
    getBase64FromFile(file.raw).then((res: any) => {
      this.headImgBase64 = res;
    });
  }
}
