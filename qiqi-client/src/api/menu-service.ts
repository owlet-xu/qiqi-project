import { AppModule } from '@/store/modules/app';
import * as httpClient from '@/utils/http-client';
import { MenuUrls } from '@/common/urls/menu-urls';
// models
import { MenuInfo } from '@/models/menu-info';
// tools
import { stringFormatArr } from '@/utils/string-utils';
import { encrypt } from '@/utils/js-encrypt-utils';

export default {
  findMenuPrivelegeTree(): Promise<MenuInfo[]> {
    const url = `${AppModule.configs.qiqiServiceUrl}${MenuUrls.findMenuPrivelegeTree}`;
    return httpClient.getPromise(url);
  },
  findRoleMenuPrivelegeTree(roleIds: string[]): Promise<MenuInfo[]> {
    // 加密
    roleIds = roleIds.map((id: string) => encrypt(id));
    const url = `${AppModule.configs.qiqiServiceUrl}${MenuUrls.findRoleMenuPrivelegeTree}`;
    return httpClient.postPromise(url, roleIds);
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
  },
  findRoleMenuPrivelegeList(roleId: string): Promise<MenuInfo[]> {
    const url = stringFormatArr(`${AppModule.configs.qiqiServiceUrl}${MenuUrls.findRoleMenuPrivelegeList}`, [roleId]);
    return httpClient.getPromise(url);
  },
  orderMenu(menuId: string, isUp: boolean): Promise<MenuInfo[]> {
    const url = stringFormatArr(`${AppModule.configs.qiqiServiceUrl}${isUp ? MenuUrls.orderMenuUp : MenuUrls.orderMenuDown}`, [menuId]);
    return httpClient.putPromise(url);
  }
};
