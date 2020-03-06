import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators';
import store from '@/store';
import { LocalStorageKeys, MylocalStorage } from '@/strorage/local-storage';

export interface ISettingsState {
  theme: string;
  fixedHeader: boolean;
  showSettings: boolean;
  showTagsView: boolean;
  showSidebarLogo: boolean;
  sidebarTextTheme: boolean;
}

@Module({ dynamic: true, store, name: 'settings' })
class Settings extends VuexModule implements ISettingsState {
  private settings: any = MylocalStorage.getObject(LocalStorageKeys.settings);
  public theme = '#1890ff';
  public fixedHeader = this.settings.fixedHeader === false ? false : true;
  public showSettings = this.settings.showSettings === false ? false : true;
  public showTagsView = this.settings.showTagsView === false ? false : true;
  public showSidebarLogo = this.settings.showSidebarLogo === false ? false : true;
  public sidebarTextTheme = this.settings.sidebarTextTheme === false ? false : true;

  @Mutation
  private CHANGE_SETTING(payload: { key: string; value: any }) {
    const { key, value } = payload;
    if (Object.prototype.hasOwnProperty.call(this, key)) {
      (this as any)[key] = value;
      this.settings[key] = value;
      MylocalStorage.setObject(LocalStorageKeys.settings, this.settings);
    }
  }

  @Action
  public ChangeSetting(payload: { key: string; value: any }) {
    this.CHANGE_SETTING(payload);
  }
}

export const SettingsModule = getModule(Settings);
