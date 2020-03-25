import { AppModule } from '@/store/modules/app';
import * as httpClient from '@/utils/http-client';
// tools
import { stringFormatArr } from '@/utils/string-utils';
import { DeptUrls } from '@/common/urls/dept-urls';
// models
import { DepartmentInfo } from '@/models/department-info';
import { PageInfo } from '@/models/page-info';


export default {
  findDepartmentTree(conditions = {}): Promise<any> {
    const url = `${AppModule.configs.qiqiServiceUrl}${DeptUrls.findDepartmentTree}`;
    return httpClient.postPromise(url, conditions);
  },
  saveDepartment(dept: DepartmentInfo) {
    const url = `${AppModule.configs.qiqiServiceUrl}${DeptUrls.saveDepartment}`;
    return httpClient.postPromise(url, dept);
  },
  remove(id: string) {
    const url = stringFormatArr(`${AppModule.configs.qiqiServiceUrl}${DeptUrls.disableDepartment}`, [id]);
    return httpClient.deletePromise(url);
  },
  getDepartmentUsers(pageInfo: PageInfo<DepartmentInfo>) {
    const url = `${AppModule.configs.qiqiServiceUrl}${DeptUrls.getDepartmentUsers}`;
    return httpClient.postPromise(url, pageInfo);
  },
  getOrderDepartmentUsers(pageInfo: PageInfo<DepartmentInfo>) {
    const url = `${AppModule.configs.qiqiServiceUrl}${DeptUrls.getOrderDepartmentUsers}`;
    return httpClient.postPromise(url, pageInfo);
  },
  saveDepartmentUsers(dept: DepartmentInfo) {
    const url = `${AppModule.configs.qiqiServiceUrl}${DeptUrls.saveDepartmentUsers}`;
    return httpClient.postPromise(url, dept);
  },
  deleteDepartmentUsers(dept: DepartmentInfo) {
    const url = `${AppModule.configs.qiqiServiceUrl}${DeptUrls.deleteDepartmentUsers}`;
    return httpClient.postPromise(url, dept);
  },
  findDepartmentListPage(pageInfo: PageInfo<DepartmentInfo>) {
    const url = `${AppModule.configs.qiqiServiceUrl}${DeptUrls.findDepartmentListPage}`;
    return httpClient.postPromise(url, pageInfo);
  }
};
