import Vue from 'vue';
import Router, { RouteConfig } from 'vue-router';
import { appRouterMap } from './modules/app-router';
import { goodsRouterMap } from './modules/goods-router';

Vue.use(Router);

export const constantRouterMap: RouteConfig[] = [...appRouterMap, ...goodsRouterMap];

const router = new Router({
  // mode: 'history',  // Disabled due to Github Pages doesn't support this, enable this if you need.
  base: process.env.BASE_URL,
  routes: constantRouterMap
});

export default router;
