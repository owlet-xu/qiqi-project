import { AppModule } from '@/store/modules/app';
import * as httpClient from '@/utils/http-client';
import { AttachUrls } from '@/common/urls/attach-urls';
// tools
import { stringFormatArr } from '@/utils/string-utils';

export default {
  uploadSingle(formData: FormData): Promise<any> {
    const url = `${AppModule.configs.attachUrl}${AttachUrls.uploadSingle}`;
    const config = { headers: { 'Content-Type': 'multipart/form-data' } };
    formData.append('metadata', '{"system":"qiqi","module":"qiqi-client","businessId":""}');
    return httpClient.postPromise(url, formData, config);
  },
  preview(fileId: string) {
    const url = stringFormatArr(`${AppModule.configs.attachUrl}${AttachUrls.preview}`, [fileId]);
    return httpClient.getPromise(url);
  },
  previewUrl(fileId: string) {
    if (!fileId) {
      return '';
    }
    return stringFormatArr(`${AppModule.configs.nginxUrl}${AttachUrls.preview}`, [fileId]);
  },
  previewVideoUrl(file: any) {
    // 建立一个可存取到该file的url
    let url = null;
    if (window.URL !== undefined) { // mozilla(firefox)
      url = window.URL.createObjectURL(file);
    }
    // } else if (window.webkitURL != undefined) { // webkit or chrome
    //   url = window.webkitURL.createObjectURL(file);
    // }
    return url;
  }
};
