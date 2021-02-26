import { Vue, Component, Watch } from 'vue-property-decorator';
// components
import goodsImgs from './goods-imgs.vue';
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
    Tinymce,
    goodsImgs
  }
})
export default class GoodsAdd extends Vue {
  private loading = false;
  private fromData: GoodsInfo = new GoodsInfo();
  headImgBase64 = '';
  private rules = {
    name: [{ required: true, validator: this.validateName, trigger: ['blur', 'change'] }],
    price: [{ required: true, validator: this.validatePrice, trigger: ['blur', 'change'] }],
    discount: [{ required: true, validator: this.validateDiscount, trigger: ['blur', 'change'] }],
    enable: [{ required: true, trigger: ['blur', 'change'] }]
  };

  created() {
    if (this.$route.query.id) {
      this.loading = true;
      GoodsService.findGoodsById(this.$route.query.id as string).then((item: GoodsInfo) => {
        this.fromData = item;
      }).finally(() => {
        this.loading = false;
      });
    }
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

  setBackgroudImg() {
    this.fromData.detail = `<div style="background-color: red">${this.fromData.detail}</div>`;
    (this.$refs['tinymce'] as any).setContent(this.fromData.detail);
    // (this.$refs['tinymce'] as any).setBackgroudImg('red');
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
        this.$message.success('保存成功');
        this.clear();
      }
    }).finally(() => {
      this.loading = false;
    });
  }
}
