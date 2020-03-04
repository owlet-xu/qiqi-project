import Vue from 'vue';
import Vuex from 'vuex';
import createLogger from 'vuex/dist/logger';

import { IAppState } from '../store/modules-store/app';
import { IUserState } from '../store/modules-store/user';
import { ISettingsState } from '../store/modules-store/settings';

Vue.use(Vuex);

export interface IRootState {
  app: IAppState;
  user: IUserState;
  settings: ISettingsState;
}

const isDev = process.env.NODE_ENV === 'development';
const store = new Vuex.Store<IRootState>({
  plugins: isDev ? [createLogger({})] : []
});

export default store;
