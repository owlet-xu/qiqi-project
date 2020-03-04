import Cookies from 'js-cookie';
import { CookiesKeys } from '@/common/constant/cookies-keys';
import { GetterTree, MutationTree, ActionTree, Module } from 'vuex';
import { AppTypes } from '../types/app-types';
// models
import { DeviceType } from '@/models/enums/device-type';

export class AppState {
  language = Cookies.get(CookiesKeys.language) || 'en';
  configs = {};
  device!: DeviceType;
  sidebar = {
    opened: true,
    withoutAnimation: true
  };
}
const mutations: MutationTree<AppState> = {
  [AppTypes.mutations.SET_LANGUAGE]: (state, language: string) => {
    state.language = language;
    Cookies.set(CookiesKeys.language, language);
  },
  [AppTypes.mutations.SET_CONFIGS]: (state, configs: any) => {
    state.configs = configs;
    Cookies.set(CookiesKeys.configs, configs);
  },
  [AppTypes.mutations.CLOSE_SIDE_BAR]: (state, withoutAnimation: boolean) => {
    state.sidebar.opened = false;
    state.sidebar.withoutAnimation = withoutAnimation;
    Cookies.set(CookiesKeys.sidebarStatusKey, 'closed');
  }
};
const actions: ActionTree<AppState, any> = {
  [AppTypes.actions.SET_LANGUAGE]({ commit }, language: string) {
    commit(AppTypes.mutations.SET_LANGUAGE, language);
  },
  [AppTypes.actions.SET_CONFIGS]({ commit }, configs: any) {
    commit(AppTypes.mutations.SET_CONFIGS, configs);
  },
  [AppTypes.actions.CLOSE_SIDE_BAR]({ commit }, withoutAnimation: boolean) {
    commit(AppTypes.mutations.CLOSE_SIDE_BAR, withoutAnimation);
  }
};
const getters: GetterTree<AppState, any> = {
  [AppTypes.getters.CONFIGS](state) {
    return state.configs;
  },
  [AppTypes.getters.LANGUAGE](state) {
    return state.language;
  }
};
// 这里是定义vuex模块
const app: Module<AppState, any> = {
  state: new AppState(),
  mutations,
  actions,
  getters
};
export default app;
