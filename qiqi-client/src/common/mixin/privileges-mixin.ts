import { Component, Vue } from 'vue-property-decorator';
import { PermissionModule } from '@/store/modules/permission';
import { UserModule } from '@/store/modules/user';
import { decrypt } from '@/utils/js-encrypt-utils';

declare module 'vue/types/vue' {
  interface Vue {
    $privileges: (routeName: string, code: string) => boolean;
  }
}
@Component
export default class PrivilegesMixin extends Vue {
  get $privileges() {
    return (routeName: string, code: string): boolean => {
      if (!UserModule.token) {
        return false;
      }
      // 超级管理员
      if (UserModule.admin && UserModule.token === decrypt(UserModule.admin)) {
        return true;
      }
      return false;
    };
  }
}
