import { AppModule } from '@/store/modules/app';
import * as httpClient from '@/utils/http-client';
import { MenuUrls } from '@/common/urls/menu-urls';
// models
import { MenuInfo } from '@/models/menu-info';
// tools
import { stringFormatArr } from '@/utils/string-utils';

export default {
  findMenuPrivelegeTree(): Promise<MenuInfo[]> {
    const url = `${AppModule.configs.qiqiServiceUrl}${MenuUrls.findMenuPrivelegeTree}`;
    return httpClient.getPromise(url);
  },
  findAllMenuPrivelegeTree(): Promise<MenuInfo[]> {
    const url = `${AppModule.configs.qiqiServiceUrl}${MenuUrls.findAllMenuPrivelegeTree}`;
    return httpClient.getPromise(url);
  },
  save(menuInfo: MenuInfo) {
    const url = `${AppModule.configs.qiqiServiceUrl}${MenuUrls.saveMenu}`;
    return httpClient.postPromise(url, menuInfo);
  },
  remove(id: string) {
    const url = stringFormatArr(`${AppModule.configs.qiqiServiceUrl}${MenuUrls.remove}`, [id]);
    return httpClient.deletePromise(url);
  },
  addMenuPrivileges(menuInfo: MenuInfo) {
    const url = `${AppModule.configs.qiqiServiceUrl}${MenuUrls.addMenuPrivileges}`;
    return httpClient.postPromise(url, menuInfo);
  },
  removeMenuPrivileges(menuInfo: MenuInfo) {
    const url = `${AppModule.configs.qiqiServiceUrl}${MenuUrls.removeMenuPrivileges}`;
    return httpClient.postPromise(url, menuInfo);
  }
};
