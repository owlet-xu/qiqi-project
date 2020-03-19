import { Route, RawLocation } from 'vue-router';
import { UserModule } from '@/store/modules/user';

export const AuthorizeGuard = (loginUrl = '/login') => {
  return async (to: Route, from: Route, next: (to?: RawLocation | false | ((vm: any) => any) | void) => void) => {
    if (UserModule.isLogin) {
      next();
    } else {
      next(`${loginUrl}?redirect=${to.fullPath}`);
    }
  };
};
