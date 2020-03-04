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
  Home = '/',
  // error
  Error = '/404'
}

export const PathPackAge = (args: string[]) => {
  const argsFilter = args.map((item: string) => (item === '/' ? '' : item)); //   解决类似不为home下路由
  return argsFilter.join('/');
};

export enum RouterName {
  // 登录
  Login = 'login',
  // 主页面
  Home = 'home',
  // error
  Error = '404'
}

export const RouterPrefix = (routerName: string) => {
  return `Router.${routerName}`;
};
