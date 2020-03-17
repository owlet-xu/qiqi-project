import { AppModule } from '@/store/modules/app';
import * as httpClient from '@/utils/http-client';
import { UserUrls } from '@/common/urls/user-urls';
// models
import { PageInfo } from '@/models/page-info';
import { UserInfo } from '@/models/user-info';

export default {
  findUserListPage(pageInfo: PageInfo<UserInfo>): Promise<any> {
    const url = `${AppModule.configs.qiqiServiceUrl}${UserUrls.findUserListPage}`;
    return httpClient.postPromise(url, pageInfo);
  },
  saveUser(user: UserInfo) {
    const url = `${AppModule.configs.qiqiServiceUrl}${UserUrls.saveUser}`;
    return httpClient.postPromise(url, user);
  }
};
