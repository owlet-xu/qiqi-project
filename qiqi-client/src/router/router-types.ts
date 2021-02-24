/*
 * @Description: 路由path管理
 * @Author: xuguoyuan
 * @Date: 2019-06-06 15:28:43
 * @LastEditTime: 2019-11-01 15:52:31
 * @LastEditors: Please set LastEditors
 */
export enum Path {
  // 登录
  Login = '/login',
  // 主页面
  Layout = '/',
  // 首页
  Home = '/home',
  // 权限管理
  Privilege = '/privilege',
  // 角色管理
  RoleManage = '/privilege/role-manage',
  // 菜单列表
  MenuManage = '/privilege/menu-manage',
  // 权限列表
  PrivilegeList = '/privilege/list',
  // 用户管理
  UserManage = '/user-manage',
  // 部门管理
  DeptManage = '/dept-manage',
  // error
  Error = '/404',
  // 商品管理
  GoodsManage = '/goods-manage',
  // 商品列表
  GoodsManageList = '/goods-manage/list',
  // 商品添加
  GoodsManageAdd = '/goods-manage/add'
}

export const PathPackAge = (args: string[]) => {
  const argsFilter = args.map((item: string) => (item === '/' ? '' : item)); //   解决类似不为home下路由
  return argsFilter.join('/');
};

export enum RouterName {
  // 登录
  Login = 'Login',
  // 主页面
  Home = 'Home',
  // 权限管理
  Privilege = 'Privilege',
  // 角色列表
  RoleManage = 'RoleManage',
  // 菜单列表
  MenuManage = 'MenuManage',
  // 权限列表
  PrivilegeList = 'PrivilegeList',
  // 用户管理
  UserManage = 'UserManage',
  // 部门管理
  DeptManage = 'DeptManage',
  // error
  Error = '404',
  // 商品管理
  GoodsManage = 'GoodsManage',
  // 商品列表
  GoodsManageList = 'GoodsManageList',
  // 商品添加
  GoodsManageAdd = 'GoodsManageAdd'
}

export const RouterPrefix = (routerName: string) => {
  return `Router.${routerName}`;
};
