import store from '@/store';
import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators';
import { UserInfo } from '@/models/user-info';

export interface IUserState {
  userInfo: UserInfo;
  token: string;
}
@Module({ dynamic: true, store, name: 'user' })
class User extends VuexModule implements IUserState {
  public userInfo = new UserInfo();
  public token = '';

  // #region mutations
  @Mutation
  private SET_TOKEN(token: string) {
    this.token = token;
  }

  @Mutation
  private SET_USER_INFO(userInfo: UserInfo) {
    this.userInfo = userInfo;
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
  // #endregion
}

export const UserModule = getModule(User);
