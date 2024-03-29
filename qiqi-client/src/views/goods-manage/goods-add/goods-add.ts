import { Vue, Component, Watch } from 'vue-property-decorator';
// components
import GoodsImgs from './goods-imgs.vue';
import Tinymce from '@/components/Tinymce/index.vue';
import { Form as ElForm } from 'element-ui';
// services
import GoodsService from '@/api/goods-service';
import AttachService from '@/api/attach-service';
// models
import { GoodsInfo } from '@/models/goods/goods-info';
// tools
import _ from 'lodash';
import { stringFormatArr } from '@/utils/string-utils';

@Component({
  components: {
    Tinymce,
    GoodsImgs
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
    this.setBackgroudImg();
    (this.$refs['tinymce'] as any).setContent('');
  }

  setBackgroudImg() {
    this.fromData.detailBg = '';
    (this.$refs['tinymce'] as any).setContentBg(this.fromData.detailBg);
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

  async save() {
    const imgs: any = await this.uploadHeadImg();
    this.fromData.file1 = imgs[0] ? AttachService.previewUrl(imgs[0][0].fileId) : this.fromData.file1;
    this.fromData.file2 = imgs[1] ? AttachService.previewUrl(imgs[1][0].fileId) : this.fromData.file2;
    this.fromData.file3 = imgs[2] ? AttachService.previewUrl(imgs[2][0].fileId) : this.fromData.file3;
    this.fromData.file4 = imgs[3] ? AttachService.previewUrl(imgs[3][0].fileId) : this.fromData.file4;
    this.fromData.file5 = imgs[4] ? AttachService.previewUrl(imgs[4][0].fileId) : this.fromData.file5;
    this.fromData.file6 = imgs[5] ? AttachService.previewUrl(imgs[5][0].fileId) : this.fromData.file6;
    GoodsService.saveGoods(this.fromData).then((res: boolean) => {
      if (res) {
        this.$message.success('保存成功');
        this.clear();
      }
    }).finally(() => {
      this.loading = false;
    });
  }

  uploadHeadImg() {
    const headImgFile = (this.$refs['goodsImgsRef'] as any).headImgFile;
    if (!headImgFile.length) {
      this.$message.warning('至少上传一张图片');
      return Promise.reject();
    }
    const requests: any = [];
    headImgFile.forEach((file: any) => {
      if (!file) {
        requests.push(Promise.resolve(''));
      } else {
        const formData = new FormData();
        formData.append('file', file);
        requests.push(AttachService.uploadSingle(formData));
      }
    });
    return Promise.all(requests);
  }
}
