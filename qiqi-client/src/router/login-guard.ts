import { Route, RawLocation } from 'vue-router';
import { UserModule } from '@/store/modules/user';
import { MenuInfo } from '@/models/menu-info';
import { RouteConfig } from 'vue-router';
import { PermissionModule } from '@/store/modules/permission';

let isMenu = false;
const checkMenu = (routes: RouteConfig[], route: Route) => {
  if (isMenu) {
    return isMenu;
  }
  if (routes.length === 0) {
    isMenu = false;
    return isMenu;
  }
  routes.forEach((item: RouteConfig) => {
    if (item.name === route.name && item.path === route.path) {
      isMenu = true;
      return;
    } else if (item.children && item.children.length) {
      checkMenu(item.children, route);
    }
  });
};

export const AuthorizeGuard = (loginUrl = '/login') => {
  return async (to: Route, from: Route, next: (to?: RawLocation | false | ((vm: any) => any) | void) => void) => {
    if (!PermissionModule.routes.length) {
      // 用户没有菜单
      next(loginUrl);
      return;
    }
    if (to.path === loginUrl) {
      next(loginUrl);
      return;
    }
    isMenu = false;
    checkMenu(PermissionModule.routes, to);
    if (UserModule.isLogin && !isMenu) {
      next(PermissionModule.routes[0].path);
    } else if (UserModule.isLogin && isMenu) {
      next();
    } else {
      next(`${loginUrl}?redirect=${to.fullPath}`);
    }
  };
};
