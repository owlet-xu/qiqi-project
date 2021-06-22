# 信息化平台快速搭建解决方案

初步想法是开发出一套基础的框架，后续业务直接在框架上扩展即可，框架包括基本的后端服务，前端web页面，移动端解决方案。



## qiqi-client 后台管理平台

基于vue + typescript + vuex + elementUI开发的后端管理平台，实现了基础功能模块：

+ 用户管理
+ 部门管理
+ 菜单管理
+ 权限管理
+ 商品管理

demo地址：http://121.196.145.103:31101/#/login



## qiqi-service 后端接口服务

基于spring boot + redis +sqlserver开发的后端接口服务，实现的基础功能有：

+ 集成redis，token验证，提高安全性
+ 登录密码加密，提供安全性



## attach-service 附件服务

基于spring boot + mongodb开发的附件服务，实现的基础功能有：

+ 利用mongdb的特性，



## qiqi-show-client 移动端解决方案

+ 基于vue + typescript + vuex + mint-ui开发的移动端解决方案



## 信息安全解决方案

1、敏感信息在传输过程中采用RSA加密方式

+ 服务端

  ```java
      @ApiOperation(value = "验证用户密码是否正确", notes = "验证用户密码是否正确")
      @ApiResponses({
              @ApiResponse(code = 200, message = "查询成功", response = UserInfo.class),
              @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
              @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
      })
      @RequestMapping(value = "/login/valid/password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<Boolean> validePassword(@RequestBody JSONObject jsonObject) {
          String token =  jsonObject.get("token").toString();
          String password =  jsonObject.get("password").toString();
          // 解密token和密码
          String tokenDe = RSAUtils.decryptByPrivate(token, RSAUtils.privateKey);
          String passwordDe = RSAUtils.decryptByPrivate(password, RSAUtils.privateKey);
          String passwordMd5 = MD5Utils.MD5(passwordDe);
          return ResponseEntity.status(HttpStatus.OK).body(loginService.validePassword(tokenDe, passwordMd5));
      }
  ```

  

+ 前端

  ```typescript
  import { encrypt } from '@/utils/js-encrypt-utils';
  
  login(loginName: string, password: string): Promise<any> {
      const url = `${AppModule.configs.qiqiServiceUrl}${LoginUrls.login}`;
      return httpClient.postPromise(url, { loginName, password: encrypt(password) });
    }
  ```

  

2、http请求加上token验证

+ 服务端

  ```java
  	@Around(value = "needVerify()")
      public Object check(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
          if (IS_CHECK_TOKEN) {
              checkToken();
          }
          return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
      }
  ```



+ 前端

  ```typescript
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
  ```

  