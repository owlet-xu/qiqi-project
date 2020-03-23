import { AppModule } from '@/store/modules/app';
import * as httpClient from '@/utils/http-client';
// tools
import { stringFormatArr } from '@/utils/string-utils';
import { DeptUrls } from '@/common/urls/dept-urls';
// models
import { DepartmentInfo } from '@/models/department-info';


export default {
  findDepartmentTree(conditions = {}): Promise<any> {
    const url = `${AppModule.configs.qiqiServiceUrl}${DeptUrls.findDepartmentTree}`;
    return httpClient.postPromise(url, conditions);
  },
  saveDepartment(user: DepartmentInfo) {
    const url = `${AppModule.configs.qiqiServiceUrl}${DeptUrls.saveDepartment}`;
    return httpClient.postPromise(url, user);
  },
  remove(id: string) {
    const url = stringFormatArr(`${AppModule.configs.qiqiServiceUrl}${DeptUrls.disableDepartment}`, [id]);
    return httpClient.deletePromise(url);
  }
};
