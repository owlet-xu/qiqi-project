import store from '@/store';
import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators';
import { UserInfo } from '@/models/user-info';
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
  public async LogOut() {
    // if (this.token === '') {
    //   throw Error('LogOut: token is undefined!');
    // }
    this.SET_TOKEN('');
    // Reset visited views and cached views
    TagsViewModule.delAllViews();
    this.SET_TOKEN('');
  }
  // #endregion
}

export const UserModule = getModule(User);
