export enum Path {
  Login = '/login',
  Layout = '/',
  Error = '/404',
  HomePage = '/home-page',
  GoodsList = '/goods-list',
  NoData = '/no-data'
}

export enum RouterName {
  Login = 'Login',
  Layout = 'Layout',
  Error = 'Error',
  HomePage = 'HomePage',
  GoodsList = 'GoodsList',
  NoData = 'NoData'
}

export const RouterPrefix = (routerName: string) => {
  return `Router.${routerName}`;
};
