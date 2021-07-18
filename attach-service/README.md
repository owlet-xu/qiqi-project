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


## 使用

### 1、在yml配置文件中配置服务器保存文件的路径

```
# app相关配置
app:
  # 图片，视频保存的物理路径，通过nginx代理访问视频和图片
  file-path: C:/files/
```



### 2、前端传送参数的时候必须指定“system”字段，用于存放图片的路径

文件可以存mongdb，可以存服务器硬盘

我们采取存硬盘，通过nginx代理，访问资源的方式

```java
    /**
     * 保存并返回文件实体对象
     *
     * @param inputStream    文件输入流
     * @param fileName       文件名
     * @param contentType    文件类型
     * @param metadataEntity 文件元数据实体对象
     * @return 文件实体对象
     */
    private FileEntity storeFileAndReturnFileEntity(InputStream inputStream, String fileName, String contentType, MetadataEntity metadataEntity) {
        // 1、存分布式文件
        // ObjectId objectId = gridFsTemplate.store(inputStream, fileName, contentType, metadataEntity);
        // String id = objectId.toString();
        // 2、存硬盘
        if (StringUtil.isNullOrEmpty(metadataEntity.getSystem())) {
            logger.error("system is null");
            throw new SystemException(ResultStatus.SYSTEM_INNER_ERROR);
        }
        String[] names = fileName.split("\\.");
        FileOutputStream fos = null;
        String id = UUID.randomUUID().toString().replaceAll("-", "") + "." + names[names.length - 1];
        try {
            byte[] bs = getBytes(inputStream);
            fos = new FileOutputStream(FILE_PATH + metadataEntity.getSystem() + "/" + id);
            fos.write(bs);// 写入数据
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(null != inputStream) inputStream.close();
                if(null != fos) fos.close();// 保存数据
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileId(id);
        fileEntity.setFileName(fileName);
        fileEntity.setContentType(contentType);

        return assembleFileMetadata(metadataEntity, fileEntity);
    }
```



### 3、前端使用

上传

```javascript
    this.headImgFile = file.raw;
    const formData = new FormData();
    formData.append('file', this.headImgFile);
    const url = `${AppModule.configs.attachUrl}${AttachUrls.uploadSingle}`;
    const config = { headers: { 'Content-Type': 'multipart/form-data' } };
    formData.append('metadata', '{"system":"qiqi","module":"qiqi-client","businessId":""}');
    return httpClient.postPromise(url, formData, config);
```

显示

```javascript
  previewUrl(fileId: string) {
    if (!fileId) {
      return '';
    }
    return stringFormatArr(`${AppModule.configs.nginxUrl}/qiqi/{fileId}`, [fileId]);
  }
```

nginx配置

```
		location /orderfood/ {    #指定图片存放路径
           root  C:/files;
           autoindex on;
        }
        ## 访问地址
        http://121.196.145.103:31111/orderfood/5dfa67a5-197e-4e92-8d5a-c479a036db03.mp4
```

