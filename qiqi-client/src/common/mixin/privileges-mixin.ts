import { Component, Vue } from 'vue-property-decorator';
// store
import { PermissionModule } from '@/store/modules/permission';
import { UserModule } from '@/store/modules/user';
// tools
import { decrypt } from '@/utils/js-encrypt-utils';
// models
import { RouteConfig } from 'vue-router';
import { PrivilegeInfo } from '@/models/privilege-info';

declare module 'vue/types/vue' {
  interface Vue {
    $privileges: (routeName: string, code: number) => boolean;
  }
}
@Component
export default class PrivilegesMixin extends Vue {
  get $privileges() {
    return (routeName: string, code: number): boolean => {
      if (!UserModule.token) {
        return false;
      }
      // 超级管理员
      if (UserModule.admin && UserModule.token === decrypt(UserModule.admin)) {
        return true;
      }
      if (!routeName || !code) {
        return false;
      }
      return this.hasPrivilege(routeName, code, false, PermissionModule.routes);
    };
  }

  /**
   * 遍历路由树
   * @param routeName 路由名称
   * @param routes 路由
   * @param hasP 是否有权限
   * @param code 权限code
   */
  hasPrivilege(routeName: string, code: number, hasP: boolean, routes: RouteConfig[] | undefined): boolean {
    if (hasP === true) {
      return hasP;
    }
    if (!routes || !Array.isArray(routes)) {
      return hasP;
    }
    routes.forEach((item: RouteConfig) => {
      if (item.name === routeName && Array.isArray(item.meta.privilegeInfos)) {
        hasP = item.meta.privilegeInfos.findIndex((p: PrivilegeInfo) => p.code === code) > -1;
        return hasP;
      }
      hasP = this.hasPrivilege(routeName, code, hasP, item.children);
    });
    return hasP;
  }
}
