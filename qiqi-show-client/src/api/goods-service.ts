import { AppModule } from '@/store/modules/app';
import * as httpClient from '@/utils/http-client';
import { GoodsUrls } from '@/common/urls/goods/goods-urls';
// models
import { PageInfo } from '@/models/page-info';
import { GoodsInfo } from '@/models/goods/goods-info';
// tools
import { stringFormatArr } from '@/utils/string-utils';

export default {
  saveGoods(good: GoodsInfo) {
    const url = `${AppModule.configs.qiqiServiceUrl}${GoodsUrls.saveGoods}`;
    return httpClient.postPromise(url, good);
  },
  findGoodsListPage(pageInfo: PageInfo<GoodsInfo>): Promise<any> {
    const url = `${AppModule.configs.qiqiServiceUrl}${GoodsUrls.findGoodsListPage}`;
    return httpClient.postPromise(url, pageInfo);
  },
  findGoodsById(id: string) {
    const url = stringFormatArr(`${AppModule.configs.qiqiServiceUrl}${GoodsUrls.findGoodsById}`, [id]);
    return httpClient.getPromise(url);
  },
  disableGoods(id: string) {
    const url = stringFormatArr(`${AppModule.configs.qiqiServiceUrl}${GoodsUrls.disableGoods}`, [id]);
    return httpClient.deletePromise(url);
  }
};
