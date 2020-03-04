import Vue from 'vue';
import Router, { RouteConfig } from 'vue-router';
import { Path, RouterName, RouterPrefix } from './router-types';
import Layout from '@/views/layout/layout.vue';

Vue.use(Router);

export const constantRouterMap: RouteConfig[] = [
  {
    path: Path.Login,
    name: RouterPrefix(RouterName.Login),
    component: () => import('@/views/login/login.vue')
  },
  {
    path: Path.Layout,
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import(/* webpackChunkName: "home" */ '@/views/home/home.vue'),
        name: 'Dashboard',
        meta: {
          title: 'dashboard',
          icon: 'dashboard',
          affix: true
        }
      }
    ]
  },
  { path: '*', redirect: Path.Error }
];

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: constantRouterMap
});

export default router;
