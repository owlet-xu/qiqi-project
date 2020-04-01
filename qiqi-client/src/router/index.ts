import Vue from 'vue';
import Router, { RouteConfig } from 'vue-router';
import { Path, RouterName, RouterPrefix } from './router-types';
import Layout from '@/views/layout/layout.vue';
import { AuthorizeGuard } from './login-guard';

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
    beforeEnter: AuthorizeGuard(Path.Login),
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
    beforeEnter: AuthorizeGuard(Path.Login),
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
    beforeEnter: AuthorizeGuard(Path.Login),
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
    beforeEnter: AuthorizeGuard(Path.Login),
    redirect: Path.RoleManage,
    meta: {
      title: RouterPrefix(RouterName.Privilege),
      icon: 'dashboard'
    },
    children: [
      {
        path: Path.RoleManage,
        component: () => import(/* webpackChunkName: "role-manage" */ '@/views/privilege-manage/role-manage.vue'),
        name: RouterPrefix(RouterName.RoleManage),
        meta: {
          title: RouterPrefix(RouterName.RoleManage),
          icon: 'dashboard'
        }
      },
      {
        path: Path.MenuManage,
        component: () => import(/* webpackChunkName: "menu-manage" */ '@/views/privilege-manage/menu-manage.vue'),
        name: RouterPrefix(RouterName.MenuManage),
        meta: {
          title: RouterPrefix(RouterName.MenuManage),
          icon: 'dashboard'
        }
      },
      {
        path: Path.PrivilegeList,
        component: () => import(/* webpackChunkName: "privilege-list" */ '@/views/privilege-manage/privilege-list.vue'),
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
