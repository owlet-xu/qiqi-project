import { UserInfo } from './user-info';

export class LoginInfo {
  token = '';
  admin = '';
  userInfo: UserInfo = new UserInfo();
}
