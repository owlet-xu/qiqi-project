import { AppModule } from '@/store/modules/app';
import * as httpClient from '@/utils/http-client';
import { PrivilegeUrls } from '@/common/urls/privilege-urls';
// models
import { PrivilegeInfo } from '@/models/privilege-info';
// tools
import { stringFormatArr } from '@/utils/string-utils';

export default {
  save(item: PrivilegeInfo): Promise<any> {
    const url = `${AppModule.configs.qiqiServiceUrl}${PrivilegeUrls.save}`;
    return httpClient.postPromise(url, item);
  },
  findAll(): Promise<any> {
    const url = `${AppModule.configs.qiqiServiceUrl}${PrivilegeUrls.findAll}`;
    return httpClient.getPromise(url);
  },
  getMenuPrivileges(menuId: string): Promise<any> {
    const url = `${AppModule.configs.qiqiServiceUrl}${stringFormatArr(PrivilegeUrls.getMenuPrivileges, [menuId])}`;
    return httpClient.getPromise(url);
  },
  getOtherMenuPrivileges(menuId: string): Promise<any> {
    const url = `${AppModule.configs.qiqiServiceUrl}${stringFormatArr(PrivilegeUrls.getOtherMenuPrivileges, [menuId])}`;
    return httpClient.getPromise(url);
  },
  remove(id: string) {
    const url = `${AppModule.configs.qiqiServiceUrl}${stringFormatArr(PrivilegeUrls.remove, [id])}`;
    return httpClient.deletePromise(url);
  }
};
