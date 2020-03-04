import store from '@/store';
import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators';
import Cookies from 'js-cookie';
import { CookiesKeys } from '@/common/constant/cookies-keys';
// models
import { DeviceType } from '@/models/enums/device-type';

export interface IAppState {
  language: string;
  configs: object;
  device: DeviceType;
  sidebar: { opened: boolean; withoutAnimation: boolean };
}

@Module({ dynamic: true, store, name: 'app' })
class App extends VuexModule implements IAppState {
  public language = Cookies.get(CookiesKeys.language) || 'zh';
  public configs = {};
  public device!: DeviceType;
  public sidebar = {
    opened: true,
    withoutAnimation: true
  };
  // #region mutations
  @Mutation
  private SET_LANGUAGE(language: string) {
    this.language = language;
    Cookies.set(CookiesKeys.language, language);
  }

  @Mutation
  private SET_CONFIGS(configs: any) {
    this.configs = configs;
    Cookies.set(CookiesKeys.configs, configs);
  }

  @Mutation
  private CLOSE_SIDE_BAR(withoutAnimation: boolean) {
    this.sidebar.opened = false;
    this.sidebar.withoutAnimation = withoutAnimation;
    Cookies.set(CookiesKeys.sidebarStatusKey, 'closed');
  }

  // #endregion

  // #region actions
  @Action
  public setLanguage(language: string) {
    this.SET_LANGUAGE(language);
  }

  @Action
  public setConfigs(configs: any) {
    this.SET_CONFIGS(configs);
  }

  @Action
  public closeSideBar(withoutAnimation: boolean) {
    this.CLOSE_SIDE_BAR(withoutAnimation);
  }
  // #endregion
}

export const AppModule = getModule(App);
