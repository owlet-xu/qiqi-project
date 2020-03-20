import { AppModule } from '@/store/modules/app';
import * as httpClient from '@/utils/http-client';
import { AttachUrls } from '@/common/urls/attach-urls';
// tools
import { stringFormatArr } from '@/utils/string-utils';

export default {
  uploadSingle(formData: FormData): Promise<any> {
    const url = `${AppModule.configs.qiqiServiceUrl}${AttachUrls.uploadSingle}`;
    const config = { headers: { 'Content-Type': 'multipart/form-data' } };
    formData.append('metadata', '{"system":"cad","module":"systemmanage","businessId":""}');
    return httpClient.postPromise(url, formData, config);
  },
  preview(fileId: string) {
    const url = stringFormatArr(`${AppModule.configs.qiqiServiceUrl}${AttachUrls.preview}`, [fileId]);
    return httpClient.getPromise(url);
  },
  previewUrl(fileId: string) {
    return stringFormatArr(`${AppModule.configs.qiqiServiceUrl}${AttachUrls.preview}`, [fileId]);
  }
};
