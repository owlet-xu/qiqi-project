import { Path, RouterName } from '../router-type';

export const businessRouterMap = [
  {
    path: Path.HomePage,
    name: RouterName.HomePage,
    component: () => import( /* webpackChunkName: "HomePage" */ '@/components/home-page/home-page.vue')
  },
  {
    path: Path.GoodsList,
    name: RouterName.GoodsList,
    component: () => import( /* webpackChunkName: "GoodsList" */ '@/components/goods-list/goods-list.vue')
  },
  {
    path: Path.NoData,
    name: RouterName.NoData,
    component: () => import( /* webpackChunkName: "noData" */ '@/components/no-data/no-data.vue')
  }
];
