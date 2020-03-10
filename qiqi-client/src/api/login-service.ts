import { AppModule } from '@/store/modules/app';
import * as httpClient from '@/utils/http-client';
import { LoginUrls } from '@/common/urls/login-urls';

export default {
  login(loginName: string, password: string): Promise<any> {
    const url = `${AppModule.configs.qiqiServiceUrl}${LoginUrls.login}`;
    return httpClient.postPromise(url, { loginName, password });
  },
};
