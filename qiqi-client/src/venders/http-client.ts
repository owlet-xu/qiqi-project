import { useRequestInterceptor, AxiosRequestConfig } from '@/utils/http-client';
import { CookiesKeys, Cookies } from '@/strorage/cookies';

useRequestInterceptor((request: AxiosRequestConfig) => {
  // 请求头中添加 Authorization Token
  const accessToken = Cookies.get(CookiesKeys.token);
  if (!request.headers['Authorization'] && accessToken && request.url && !request.url.includes('v3/oauth/token')) {
    request.headers['Authorization'] = `${accessToken}`;
  }
  return request;
});
