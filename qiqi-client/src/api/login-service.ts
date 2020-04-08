import * as httpClient from '@/utils/http-client';
import { LoginUrls } from '@/common/urls/login-urls';
// store
import { AppModule } from '@/store/modules/app';
import { UserModule } from '@/store/modules/user';
// tools
import { encrypt } from '@/utils/js-encrypt-utils';
import { stringFormatArr } from '@/utils/string-utils';

export default {
  login(loginName: string, password: string): Promise<any> {
    const url = `${AppModule.configs.qiqiServiceUrl}${LoginUrls.login}`;
    return httpClient.postPromise(url, { loginName, password: encrypt(password) });
  },
  newPassword(password: string): Promise<any> {
    const url = `${AppModule.configs.qiqiServiceUrl}${LoginUrls.newPassword}`;
    return httpClient.postPromise(url, { token: encrypt(UserModule.token), password: encrypt(password) });
  },
  resetPassword(): Promise<any> {
    const url = `${AppModule.configs.qiqiServiceUrl}${LoginUrls.resetPassword}`;
    return httpClient.postPromise(url, { token: encrypt(UserModule.token) });
  },
  validePassword(password: string): Promise<any> {
    const url = `${AppModule.configs.qiqiServiceUrl}${LoginUrls.validePassword}`;
    return httpClient.postPromise(url, { token: encrypt(UserModule.token), password: encrypt(password) });
  },
  checkAdmin() {
    const url = `${AppModule.configs.qiqiServiceUrl}${LoginUrls.checkAdmin}`;
    return httpClient.postPromise(url, { token: encrypt(UserModule.token) });
  }
};
