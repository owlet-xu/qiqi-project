import { RouteConfig } from 'vue-router';
import { Path, RouterName, RouterPrefix } from '../router-types';
import Layout from '@/views/layout/layout.vue';
import { AuthorizeGuard } from '../login-guard';

export const goodsRouterMap: RouteConfig[] = [
    {
        path: Path.GoodsManage,
        component: Layout,
        beforeEnter: AuthorizeGuard(Path.Login),
        redirect: Path.GoodsManageList,
        meta: {
            title: RouterPrefix(RouterName.GoodsManageList),
            icon: 'dashboard'
        },
        children: [
            {
                path: Path.GoodsManageList,
                component: () => import(/* webpackChunkName: "goods-manage" */ '@/views/goods-manage/goods-manage.vue'),
                name: RouterPrefix(RouterName.GoodsManageList),
                meta: {
                    title: RouterPrefix(RouterName.GoodsManageList),
                    icon: 'dashboard',
                    noCache: false
                }
            },
            {
                path: Path.GoodsManageAdd,
                component: () => import(/* webpackChunkName: "goods-manage-add" */ '@/views/goods-manage/goods-add/goods-add.vue'),
                name: RouterPrefix(RouterName.GoodsManageAdd),
                meta: {
                    title: RouterPrefix(RouterName.GoodsManageAdd),
                    icon: 'dashboard',
                    noCache: true
                }
            }
        ]
    }
];
