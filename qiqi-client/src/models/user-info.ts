import { DepartmentInfo } from './department-info';
import { RoleInfo } from './role-info';
export class UserInfo {
  id = '';
  /**
   * 姓名
   */
  name = '';
  /**
   * 用户名
   */
  userName = '';
  /**
   * 密码
   */
  password = '';
  /**
   * 旧密码
   */
  passwordOld = '';
  /**
   * 新密码
   */
  passwordConfirm = '';
  /**
   * 手机号
   */
  mobile = '';
  /**
   * 邮箱
   */
  email = '';
  /**
   * 用户类型
   */
  userType = '';
  /**
   * 启用禁用
   */
  enable = 1;
  /**
   * 用户积分
   */
  userIntegral = 0;
  /**
   * 用户等级
   */
  userLevel = '';
  /**
   * 更新时间
   */
  updateTime?: Date;
  /**
   * 创建时间
   */
  createTime?: Date;
  headImg = '';
  deptInfos: DepartmentInfo[] = [];
  roleInfos: RoleInfo[] = [];
}
