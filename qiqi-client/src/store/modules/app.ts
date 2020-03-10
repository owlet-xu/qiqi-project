import store from '@/store';
import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators';
import { CookiesKeys, Cookies } from '@/strorage/cookies';
// models
import { DeviceType } from '@/models/enums/device-type';

export interface IAppState {
  language: string;
  configs: object;
  device: DeviceType;
  sidebar: { opened: boolean; withoutAnimation: boolean };
  size: string;
}

@Module({ dynamic: true, store, name: 'app' })
class App extends VuexModule implements IAppState {
  public language = Cookies.get(CookiesKeys.language) || 'zh';
  public size = Cookies.get(CookiesKeys.size) || 'medium';
  public configs: any = {};
  public device = DeviceType.Desktop;
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
  private CLOSE_SIDEBAR(withoutAnimation: boolean) {
    this.sidebar.opened = false;
    this.sidebar.withoutAnimation = withoutAnimation;
    Cookies.set(CookiesKeys.sidebarStatusKey, 'closed');
  }

  @Mutation
  private TOGGLE_DEVICE(device: DeviceType) {
    this.device = device;
  }

  @Mutation
  private TOGGLE_SIDEBAR(withoutAnimation: boolean) {
    this.sidebar.opened = !this.sidebar.opened;
    this.sidebar.withoutAnimation = withoutAnimation;
    if (this.sidebar.opened) {
      Cookies.set(CookiesKeys.sidebarStatusKey, 'opened');
    } else {
      Cookies.set(CookiesKeys.sidebarStatusKey, 'closed');
    }
  }

  @Mutation
  private SET_SIZE(size: string) {
    this.size = size;
    Cookies.set(CookiesKeys.size, this.size);
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
  public CloseSideBar(withoutAnimation: boolean) {
    this.CLOSE_SIDEBAR(withoutAnimation);
  }

  @Action
  public ToggleDevice(device: DeviceType) {
    this.TOGGLE_DEVICE(device);
  }

  @Action
  public ToggleSideBar(withoutAnimation: boolean) {
    this.TOGGLE_SIDEBAR(withoutAnimation);
  }

  @Action
  public SetSize(size: string) {
    this.SET_SIZE(size);
  }

  // #endregion
}

export const AppModule = getModule(App);
