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
    redirect: Path.Home,
    children: [
      {
        path: Path.Home,
        component: () => import(/* webpackChunkName: "home" */ '@/views/home/home.vue'),
        name: RouterPrefix(RouterName.Home),
        meta: {
          title: RouterPrefix(RouterName.Home),
          icon: 'dashboard',
          affix: true
        }
      }
    ]
  },
  {
    path: Path.UserManage,
    component: Layout,
    children: [
      {
        path: Path.UserManage,
        component: () => import(/* webpackChunkName: "icons" */ '@/views/user-manage/user-manage.vue'),
        name: RouterPrefix(RouterName.UserManage),
        meta: {
          title: RouterPrefix(RouterName.UserManage),
          icon: 'icon',
          noCache: true
        }
      }
    ]
  },
  {
    path: Path.DeptManage,
    component: Layout,
    children: [
      {
        path: Path.DeptManage,
        component: () => import(/* webpackChunkName: "icons" */ '@/views/dept-manage/dept-manage.vue'),
        name: RouterPrefix(RouterName.DeptManage),
        meta: {
          title: RouterPrefix(RouterName.DeptManage),
          icon: 'icon',
          noCache: true
        }
      }
    ]
  },
  {
    path: Path.Privilege,
    component: Layout,
    redirect: Path.RoleList,
    meta: {
      title: RouterPrefix(RouterName.Privilege),
      icon: 'dashboard'
    },
    children: [
      {
        path: Path.RoleList,
        component: () => import(/* webpackChunkName: "role-list" */ '@/views/privilege-manage/role-list.vue'),
        name: RouterPrefix(RouterName.RoleList),
        meta: {
          title: RouterPrefix(RouterName.RoleList),
          icon: 'dashboard'
        }
      },
      {
        path: Path.MenuList,
        component: () => import(/* webpackChunkName: "menu-list" */ '@/views/privilege-manage/menu-list.vue'),
        name: RouterPrefix(RouterName.MenuList),
        meta: {
          title: RouterPrefix(RouterName.MenuList),
          icon: 'dashboard'
        }
      },
      {
        path: Path.PrivilegeList,
        component: () => import(/* webpackChunkName: "home" */ '@/views/privilege-manage/privilege-list.vue'),
        name: RouterPrefix(RouterName.PrivilegeList),
        meta: {
          title: RouterPrefix(RouterName.PrivilegeList),
          icon: 'dashboard'
        }
      }
    ]
  },
  { path: '*', redirect: Path.Error }
];

const router = new Router({
  // mode: 'history',  // Disabled due to Github Pages doesn't support this, enable this if you need.
  base: process.env.BASE_URL,
  routes: constantRouterMap
});

export default router;
