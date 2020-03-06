import { VuexModule, Module, getModule, Mutation, Action } from 'vuex-module-decorators';
import { RouteConfig } from 'vue-router';
import store from '@/store';
import { constantRouterMap } from '@/router';
import { CookiesKeys, Cookies } from '@/strorage/cookies';

export interface IPermissionState {
  routes: RouteConfig[];
  dynamicRoutes: RouteConfig[];
}

@Module({ dynamic: true, store, name: 'permission' })
class Permission extends VuexModule implements IPermissionState {
  public routes: RouteConfig[] = Cookies.getJSON(CookiesKeys.menus) || [];
  public dynamicRoutes: RouteConfig[] = [];

  @Mutation
  private SET_ROUTES(routes: RouteConfig[]) {
    this.routes = routes;
    Cookies.set(CookiesKeys.menus, this.routes);
  }

  @Action
  public GenerateRoutes(routes: RouteConfig[]) {
    this.SET_ROUTES(routes);
  }
}

export const PermissionModule = getModule(Permission);
