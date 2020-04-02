import { AppModule } from '@/store/modules/app';
import * as httpClient from '@/utils/http-client';
import { RoleUrls } from '@/common/urls/role-urls';
// models
import { RoleInfo } from '@/models/role-info';
// tools
import { stringFormatArr } from '@/utils/string-utils';

export default {
  findList(): Promise<any> {
    const url = `${AppModule.configs.qiqiServiceUrl}${RoleUrls.findList}`;
    return httpClient.getPromise(url);
  },
  findEnableList(): Promise<any> {
    const url = `${AppModule.configs.qiqiServiceUrl}${RoleUrls.findEnableList}`;
    return httpClient.getPromise(url);
  },
  save(roleInfo: RoleInfo): Promise<any> {
    const url = `${AppModule.configs.qiqiServiceUrl}${RoleUrls.save}`;
    return httpClient.postPromise(url, roleInfo);
  },
  remove(id: string) {
    const url = `${AppModule.configs.qiqiServiceUrl}${stringFormatArr(RoleUrls.remove, [id])}`;
    return httpClient.deletePromise(url);
  },
  saveRoleMenuPrivilege(roleInfo: RoleInfo) {
    const url = `${AppModule.configs.qiqiServiceUrl}${RoleUrls.saveRoleMenuPrivilege}`;
    return httpClient.postPromise(url, roleInfo);
  }
};
