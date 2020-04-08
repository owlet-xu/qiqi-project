import store from '@/store';
import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators';
import { UserInfo } from '@/models/user-info';
import { Vue } from 'vue-property-decorator';
import { Path } from '@/router/router-types';
// store
import { TagsViewModule } from './tags-view';
import { CookiesKeys, Cookies } from '@/strorage/cookies';

export interface IUserState {
  userInfo: UserInfo;
  token: string;
}
@Module({ dynamic: true, store, name: 'user' })
class User extends VuexModule implements IUserState {
  public userInfo = Cookies.getJSON(CookiesKeys.userInfo) || new UserInfo();
  public token = Cookies.get(CookiesKeys.token) || '';
  public admin = Cookies.get(CookiesKeys.admin) || '';

  get isLogin() {
    return this.token ? true : false;
  }

  // #region mutations
  @Mutation
  private SET_TOKEN(token: string) {
    this.token = token;
    Cookies.set(CookiesKeys.token, token);
  }

  @Mutation
  private SET_USER_INFO(userInfo: UserInfo) {
    this.userInfo = userInfo;
    Cookies.set(CookiesKeys.userInfo, userInfo);
  }

  @Mutation
  private SET_ADMIN(admin: string) {
    this.admin = admin;
    Cookies.set(CookiesKeys.admin, admin);
  }

  // #endregion

  // #region actions
  @Action
  public setToken(token: string) {
    this.SET_TOKEN(token);
  }

  @Action
  public setUserInfo(userInfo: UserInfo) {
    this.SET_USER_INFO(userInfo);
  }

  @Action
  public async LogOut(v: Vue | null, isTokenValid: boolean) {
    TagsViewModule.delAllViews();
    this.SET_TOKEN('');
    if (v && isTokenValid) {
      v.$message(v.$t('TokenValid') as string);
      v.$router.push(`${Path.Login}?redirect=${v.$route.fullPath}`);
    } else if (v) {
      v.$router.push(`${Path.Login}`);
    }
  }

  @Action
  public setAdmin(admin: string) {
    this.SET_ADMIN(admin);
  }
  // #endregion
}

export const UserModule = getModule(User);
