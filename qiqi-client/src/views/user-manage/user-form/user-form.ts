import { Vue, Component, Prop } from 'vue-property-decorator';
import { Form as ElForm } from 'element-ui';
import AttachService from '@/api/attach-service';
// services
import UserService from '@/api/user-service';
import DeptService from '@/api/dept-service';
// tools
import { stringFormatArr } from '@/utils/string-utils';
import { getBase64FromFile } from '@/utils/base64-utils';
// store
import { UserModule } from '@/store/modules/user';
// models
import { UserInfo } from '@/models/user-info';
import { DepartmentInfo } from '@/models/department-info';
import { PageInfo } from '@/models/page-info';

@Component
export default class UserForm extends Vue {
  @Prop()
  userInfo!: UserInfo; // 表单数据

  @Prop({ default: false })
  saving!: boolean; // 是否正在保存中
  private headImgBase64: any = ''; // 显示的图片
  private headImgFile: any = ''; // 上传的文件
  private depts: DepartmentInfo[] = [];

  private rules = {
    userName: [{ required: true, validator: this.validateUserName, trigger: ['blur', 'change'] }],
    name: [{ required: true, validator: this.validateName, trigger: ['blur', 'change'] }]
  };

  get fromData() {
    return this.userInfo;
  }

  set fromData(value) {
    this.$emit('update:userInfo', value);
  }

  get savingTemp() {
    return this.saving;
  }

  set savingTemp(value) {
    this.$emit('update:saving', value);
  }

  created() {
    this.savingTemp = false;
    if (this.userInfo.headImg) {
      this.headImgBase64 = AttachService.previewUrl(this.userInfo.headImg);
    }
    this.findDepartmentListPage();
  }

  findDepartmentListPage() {
    const pageInfo: PageInfo<DepartmentInfo> = new PageInfo();
    pageInfo.page = 0;
    pageInfo.size = 999999;
    pageInfo.conditions = new DepartmentInfo();
    DeptService.findDepartmentListPage(pageInfo).then((res: PageInfo<DepartmentInfo>) => {
      this.depts = res.contents;
    });
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

  private async save() {
    this.savingTemp = true;
    const img: any = await this.uploadHeadImg();
    if (img && img.fileName) {
      this.fromData.headImg = img.fileName;
    }
    this.fromData.userType = '1';
    UserService.saveUser(this.fromData)
      .then((res: any) => {
        this.changeCurrentUserInfo();
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

  /**
   * 如果修改的是登录用户，相关信息要更新
   */
  changeCurrentUserInfo() {
    if (UserModule.userInfo.id === this.fromData.id) {
      UserModule.setUserInfo(this.fromData);
    }
  }
}
