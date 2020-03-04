import Vue from 'vue';
import Vuex from 'vuex';
import createLogger from 'vuex/dist/logger';

import { IAppState } from '../store/modules/app';
import { IUserState } from '../store/modules/user';
import { ISettingsState } from '../store/modules/settings';
import { ITagsViewState } from '../store/modules/tags-view';
import { IErrorLogState } from './modules/error-log';

Vue.use(Vuex);

export interface IRootState {
  app: IAppState;
  user: IUserState;
  settings: ISettingsState;
  tagsView: ITagsViewState;
  errorLog: IErrorLogState;
}

const isDev = process.env.NODE_ENV === 'development';
const store = new Vuex.Store<IRootState>({
  plugins: isDev ? [createLogger({})] : []
});

export default store;
