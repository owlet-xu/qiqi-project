# attach-service 附件服务

## 服务简介

+ 可以上传和下载各种文件
+ 集成mongodb，具有关系数据库的功能
+ 文件保存在本地，通过nginx代理图片和视频

### 快速开始

1、下载代码到本地

```
git clone https://github.com/owlet-xu/qiqi-project.git
```

2、用idea引入项目，并配置application.yml

- 服务端口配置

  ```
  server:
    port: 32100 
  ```

- 数据库链接配置

  ```
    data:
      mongodb:
        uri: mongodb://sa:123456@169.254.59.111:27017/qiqi_db
        database: qiqi_db
    servlet:
      multipart:
        max-file-size: 100MB
        max-request-size: 110MB
  
    jackson:
      date-format: yyyy-MM-dd HH:mm:ss  #日期序列化格式
  ```

3、在Application.java中启动项目

![springbootstart](D:/Code%20Github/qiqi-project/qiqi-service/docs/imgs/springbootstart.jpg "springbootstart")



### 部署

1、在Terminal命令窗口中打包框架代码

```
gradlew clean build
```

2、在build目录中找到打包后的jar程序

3、在服务器制作服务

- centos中

  ```
  nohup java -jar qiqi-service-2.0.0.jar &
  ```

- windows中

  ```
  // 使用nssm.exe制作windows服务
  ```

  

  

