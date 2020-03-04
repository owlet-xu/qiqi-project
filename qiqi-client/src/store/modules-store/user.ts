
import { GetterTree, MutationTree, ActionTree, Module } from 'vuex';
import { UserTypes } from '../types/user-types';
import { UserInfo } from '@/models/user-info';

export class UserState {
  userInfo: UserInfo = new UserInfo();
  token = '';
}

const mutations: MutationTree<UserState> = {
  [UserTypes.mutations.SET_TOKEN]: (state, token: string) => {
    state.token = token;
  },
  [UserTypes.mutations.SET_USER_INFO]: (state, userInfo: UserInfo) => {
    state.userInfo = userInfo;
  }
};
const actions: ActionTree<UserState, any> = {
  [UserTypes.actions.SET_TOKEN]({ commit }, token: string) {
    commit(UserTypes.mutations.SET_TOKEN, token);
  },
  [UserTypes.actions.SET_USER_INFO]({ commit }, userInfo: UserInfo) {
    commit(UserTypes.mutations.SET_USER_INFO, userInfo);
  }
};
const getters: GetterTree<UserState, any> = {
  [UserTypes.getters.USER_INFO](state) {
    return state.userInfo;
  },
  [UserTypes.getters.TOKEN](state) {
    return state.token;
  }
};
// 这里是定义vuex模块
const user: Module<UserState, any> = {
  state: new UserState(),
  mutations,
  actions,
  getters
};
export default user;
