# 快速开发解决方案

初步想法是开发出一套基础的框架，后续业务直接在框架上扩展即可，框架包括基本的后端服务，前端web页面，移动端解决方案。



## qiqi-client 后台管理平台

基于vue + typescript开发的后端管理平台，实现了基础功能模块：

+ 用户管理
+ 部门管理
+ 菜单管理
+ 权限管理
+ 商品管理

## qiqi-service 后端接口服务

基于spring boot开发的后端接口服务，实现的基础功能有：

+ 集成redis，token验证，提高安全性
+ 登录密码加密，提供安全性



## attach-service 附件服务

基于spring boot + mongdb开发的附件服务，实现的基础功能有：

+ 利用mongdb的特性，



## qiqi-show-client 移动端解决方案

+ 基于vue + typescript + 