import { Vue, Component, Prop } from 'vue-property-decorator';
import { UserInfo } from '@/models/user-info';
import { stringFormatArr } from '@/utils/string-utils';
import { Form as ElForm } from 'element-ui';

@Component
export default class UserForm extends Vue {
  @Prop()
  userInfo!: UserInfo;

  private rules = {
    userName: [{ required: true, validator: this.validateUserName, trigger: ['blur', 'change'] }],
    name: [{ required: true, validator: this.validateName, trigger: ['blur', 'change'] }]
  };

  get fromData() {
    return this.userInfo;
  }

  async validForm(): Promise<boolean> {
    const form: ElForm = this.$refs['formRef'] as ElForm;
    return form.validate();
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
}
