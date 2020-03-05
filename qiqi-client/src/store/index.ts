import Vue from 'vue';
import Vuex from 'vuex';
import createLogger from 'vuex/dist/logger';

import { IAppState } from '../store/modules/app';
import { IUserState } from '../store/modules/user';
import { ISettingsState } from '../store/modules/settings';
import { ITagsViewState } from '../store/modules/tags-view';
import { IErrorLogState } from './modules/error-log';
import { IPermissionState } from './modules/permission';

Vue.use(Vuex);

export interface IRootState {
  app: IAppState;
  user: IUserState;
  settings: ISettingsState;
  tagsView: ITagsViewState;
  errorLog: IErrorLogState;
  permission: IPermissionState;
}

const isDev = process.env.NODE_ENV === 'development';
const store = new Vuex.Store<IRootState>({
  // 开启了vuex logger会非常卡，因为会在console.log界面输出缓存的所有仓库，包括界面
  // plugins: isDev ? [createLogger({})] : []
});

export default store;
