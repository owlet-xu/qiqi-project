import { Vue, Component, Prop } from 'vue-property-decorator';
import { DepartmentInfo } from '@/models/department-info';
import { Form as ElForm } from 'element-ui';
import AttachService from '@/api/attach-service';
// services
import DeptService from '@/api/dept-service';
// tools
import { stringFormatArr } from '@/utils/string-utils';
import { getBase64FromFile } from '@/utils/base64-utils';

@Component
export default class DeptForm extends Vue {
  @Prop()
  deptInfo!: DepartmentInfo; // 表单数据

  @Prop({ default: false })
  saving!: boolean; // 是否正在保存中
  private headImgBase64: any = ''; // 显示的图片
  private headImgFile: any = ''; // 上传的文件

  private rules = {
    name: [{ required: true, validator: this.validateName, trigger: ['blur', 'change'] }]
  };

  get fromData() {
    return this.deptInfo;
  }

  set fromData(value) {
    this.$emit('update:deptInfo', value);
  }

  get savingTemp() {
    return this.saving;
  }

  set savingTemp(value) {
    this.$emit('update:saving', value);
  }

  created() {
    if (this.deptInfo.imgId) {
      this.headImgBase64 = AttachService.previewUrl(this.deptInfo.imgId);
    }
  }

  /**
   * 验证表单-暴漏给父组件
   */
  public async validForm(): Promise<boolean> {
    const form: ElForm = this.$refs['formRef'] as ElForm;
    return form.validate();
  }

  /**
   * 保存表单--暴漏给父组件
   */
  public saveValid() {
    this.validForm()
      .then((valid: boolean) => {
        if (valid) {
          this.save();
        }
      })
      .catch(() => {});
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

  private async save() {
    this.savingTemp = true;
    const img: any = await this.uploadHeadImg();
    if (img && img.fileName) {
      this.fromData.imgId = img.fileName;
    }
    DeptService.saveDepartment(this.fromData)
      .then((res: any) => {
        // 父组件回调函数
        this.$emit('saveSuccess', res);
      })
      .finally(() => {
        this.savingTemp = false;
      });
  }

  uploadHeadImg() {
    if (!this.headImgFile) {
      return null;
    }
    const formData = new FormData();
    formData.append('file', this.headImgFile);
    return AttachService.uploadSingle(formData);
  }
}
