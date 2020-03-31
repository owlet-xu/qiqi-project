import { Vue, Component, Prop } from 'vue-property-decorator';
import { RoleInfo } from '@/models/role-info';
import { Form as ElForm } from 'element-ui';
// services
import RoleService from '@/api/role-service';
// tools
import { stringFormatArr } from '@/utils/string-utils';

@Component
export default class RoleForm extends Vue {
  @Prop()
  roleInfo!: RoleInfo; // 表单数据

  @Prop({ default: false })
  saving!: boolean; // 是否正在保存中

  private rules = {
    name: [{ required: true, validator: this.validateName, trigger: ['blur', 'change'] }]
  };

  get fromData() {
    return this.roleInfo;
  }

  set fromData(value) {
    this.$emit('update:roleInfo', value);
  }

  get savingTemp() {
    return this.saving;
  }

  set savingTemp(value) {
    this.$emit('update:saving', value);
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

  private async save() {
    this.savingTemp = true;
    RoleService.save(this.fromData)
      .then((res: any) => {
        // 父组件回调函数
        this.$emit('saveSuccess', res);
      })
      .finally(() => {
        this.savingTemp = false;
      });
  }
}
