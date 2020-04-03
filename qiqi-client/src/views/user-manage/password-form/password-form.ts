import { Vue, Component, Prop } from 'vue-property-decorator';
import { Form as ElForm, Avatar } from 'element-ui';
// services
import LoginService from '@/api/login-service';
// tools
import { stringFormatArr } from '@/utils/string-utils';
import { Path } from '@/router/router-types';
import _ from 'lodash';
// store
import { UserModule } from '@/store/modules/user';
// models
import { UserInfo } from '@/models/user-info';

@Component
export default class PasswordForm extends Vue {
  @Prop()
  userInfo!: UserInfo; // 表单数据

  @Prop({ default: false })
  saving!: boolean; // 是否正在保存中

  private isOld = false;

  private rules = {
    passwordOld: [{ required: true, validator: this.validatePasswordOld, trigger: ['blur', 'change'] }],
    password: [{ required: true, validator: this.validatePassword, trigger: ['blur', 'change'] }],
    passwordConfirm: [{ required: true, validator: this.validatePasswordConfirm, trigger: ['blur', 'change'] }]
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

  /**
   * 重置密码--暴漏给父组件
   */
  public resetPassword() {
    this.savingTemp = true;
    LoginService.resetPassword()
      .then((res: any) => {
        this.changeCurrentUserInfo();
        // 父组件回调函数
        this.$emit('saveSuccess', res);
      })
      .finally(() => {
        this.savingTemp = false;
      });
  }

  private validatePasswordOld(rule: any, value: any, callback: any) {
    console.log('---aa');
    if (!value || value.length === 0) {
      callback(new Error(stringFormatArr(this.$t('NotEmptyTip').toString(), [this.$t('Login.OldPassword').toString()])));
      return;
    }
    LoginService.validePassword(this.fromData.passwordOld)
      .then((res: boolean) => {
        if (res === true) {
          callback();
        } else {
          callback(new Error(this.$t('Login.OldPasswordError').toString()));
        }
      })
      .catch((e: any) => {
        if (e.errorCode === 22005) {
          this.LogOut();
        }
      });
  }

  private validatePassword(rule: any, value: any, callback: any) {
    if (!value || value.length === 0) {
      callback(new Error(stringFormatArr(this.$t('NotEmptyTip') as string, [this.$t('Login.NewPassword') as string])));
    } else if (value.length < 4) {
      callback(new Error(stringFormatArr(this.$t('MinStringLenTip').toString(), [this.$t('Login.NewPassword').toString(), '4'])));
      return;
    } else {
      callback();
    }
  }

  private validatePasswordConfirm(rule: any, value: any, callback: any) {
    if (!value || value.length === 0) {
      callback(new Error(stringFormatArr(this.$t('NotEmptyTip') as string, [this.$t('Login.ConfirmPassword') as string])));
    } else if (this.fromData.password !== value) {
      callback(new Error(this.$t('Login.ConfirmPasswordError') as string));
    } else {
      callback();
    }
  }

  private async save() {
    this.savingTemp = true;
    this.fromData.userType = '1';
    LoginService.newPassword(this.fromData.passwordConfirm)
      .then((res: any) => {
        this.changeCurrentUserInfo();
        // 父组件回调函数
        this.$emit('saveSuccess', res);
      })
      .finally(() => {
        this.savingTemp = false;
      });
  }

  /**
   * 如果修改的是登录用户，相关信息要更新
   */
  changeCurrentUserInfo() {
    if (UserModule.userInfo.id === this.fromData.id) {
      UserModule.setUserInfo(this.fromData);
      // 重新登录
      this.LogOut();
    }
  }

  private LogOut() {
    UserModule.LogOut(this);
  }
}
