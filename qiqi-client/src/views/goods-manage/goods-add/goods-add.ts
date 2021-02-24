import { Vue, Component, Watch } from 'vue-property-decorator';
// components
import Tinymce from '@/components/Tinymce/index.vue';
import { Form as ElForm } from 'element-ui';
// services
import GoodsService from '@/api/goods-service';
// models
import { GoodsInfo } from '@/models/goods/goods-info';
// tools
import _ from 'lodash';
import { stringFormatArr } from '@/utils/string-utils';

@Component({
  components: {
    Tinymce
  }
})
export default class GoodsAdd extends Vue {
  private loading = false;
  private fromData: GoodsInfo = new GoodsInfo();

  private rules = {
    name: [{ required: true, validator: this.validateName, trigger: ['blur', 'change'] }],
    price: [{ required: true, validator: this.validatePrice, trigger: ['blur', 'change'] }],
    discount: [{ required: true, validator: this.validateDiscount, trigger: ['blur', 'change'] }]
  };

  created() {
    console.log(this.$route.query.id, '---ddd');
  }

  private validatePrice(rule: any, value: any, callback: any) {
    if (!value || value <= 0) {
      callback(new Error(stringFormatArr(this.$t('FomateErrorTip').toString(), [this.$t('Goods.Price').toString()])));
    } else {
      callback();
    }
  }

  private validateDiscount(rule: any, value: any, callback: any) {
    if (!value || value <= 0) {
      callback(new Error(stringFormatArr(this.$t('FomateErrorTip').toString(), [this.$t('Goods.Discount').toString()])));
    } else {
      callback();
    }
  }

  private validateName(rule: any, value: any, callback: any) {
    if (!value || value.length === 0) {
      callback(new Error(stringFormatArr(this.$t('NotEmptyTip').toString(), [this.$t('Goods.Name').toString()])));
    } else {
      callback();
    }
  }

  clear() {
    this.fromData = new GoodsInfo();
    (this.$refs['tinymce'] as any).setContent('');
  }

  saveValidate() {
    if (!this.fromData.detail) {
      this.$message.warning('请填写商品详情');
      return;
    } else if (this.fromData.detail.length > 4000) {
      this.$message.warning(stringFormatArr(this.$t('MaxStringLenTip').toString(), ['商品详情', '4000']));
      return;
    }
    const form: ElForm = this.$refs['formRef'] as ElForm;
    form.validate().then((valid: boolean) => {
      if (valid) {
        this.save();
      }
    }).catch(() => { });
  }

  save() {
    this.loading = true;
    GoodsService.saveGoods(this.fromData).then((res: boolean) => {
      if (res) {
        this.clear();
      }
    }).finally(() => {
      this.loading = false;
    });
  }
}
