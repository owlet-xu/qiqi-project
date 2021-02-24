import { Vue, Component, Watch } from 'vue-property-decorator';
// components
import Pagination from '@/components/Pagination/index.vue';
// services
import UserService from '@/api/user-service';
// models
import { GoodsInfo } from '@/models/goods/goods-info';
// tools
import _ from 'lodash';
import { stringFormatArr } from '@/utils/string-utils';
import loginService from '@/api/login-service';

@Component({
  components: {
    Pagination
  }
})
export default class GoodsAdd extends Vue {
  private loading = false;
  private loadingSave = false;
  private fromData: GoodsInfo = new GoodsInfo();

  private rules = {
    name: [{ required: true, validator: this.validateUserName, trigger: ['blur', 'change'] }],
    price: [{ required: true, validator: this.validateName, trigger: ['blur', 'change'] }]
  };

  created() {
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
