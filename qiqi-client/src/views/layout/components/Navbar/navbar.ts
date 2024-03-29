import { Component, Vue } from 'vue-property-decorator';
// components
import UserForm from '@/views/user-manage/user-form/user-form';
import PasswordForm from '@/views/user-manage/password-form/password-form';
import Breadcrumb from '@/components/Breadcrumb/index.vue';
import ErrorLog from '@/components/ErrorLog/index.vue';
import Hamburger from '@/components/Hamburger/index.vue';
import HeaderSearch from '@/components/HeaderSearch/index.vue';
import LangSelect from '@/components/lang-select/index';
import Screenfull from '@/components/Screenfull/index.vue';
import SizeSelect from '@/components/SizeSelect/index.vue';
// services
import AttachService from '@/api/attach-service';
// models
import { UserInfo } from '@/models/user-info';
// store
import { AppModule } from '@/store/modules/app';
import { UserModule } from '@/store/modules/user';
// tools
import _ from 'lodash';
/**
 * 头部
 */
@Component({
  name: 'Navbar',
  components: {
    Breadcrumb,
    ErrorLog,
    Hamburger,
    HeaderSearch,
    LangSelect,
    Screenfull,
    SizeSelect,
    UserForm,
    PasswordForm
  }
})
export default class extends Vue {
  private loadingSave = false;
  private showEditDialog = false;
  private showPasswordDialog = false;
  private userInfoSelected = new UserInfo();

  get sidebar() {
    return AppModule.sidebar;
  }

  get device() {
    return AppModule.device.toString();
  }

  get avatar() {
    if (UserModule.userInfo.headImg) {
     return AttachService.previewUrl(UserModule.userInfo.headImg);
    } else {
      return '@/assets/imgs/owlet.png';
    }
  }

  private toggleSideBar() {
    AppModule.ToggleSideBar(false);
  }

  private async LogOut() {
    await UserModule.LogOut(this, false);
  }

  edit() {
    this.userInfoSelected = _.cloneDeep(UserModule.userInfo);
    this.showEditDialog = true;
  }

  save() {
    const form: UserForm = this.$refs['navBarUserFormRef'] as UserForm;
    form.saveValid();
  }

  saveSuccess() {
    this.showEditDialog = false;
  }

  editPassword(item: UserInfo) {
    this.showPasswordDialog = true;
  }

  savePassword() {
    const form: PasswordForm = this.$refs['navBarPasswordFormRef'] as PasswordForm;
    form.saveValid();
  }

  savePasswordSuccess() {
    this.showPasswordDialog = false;
    this.LogOut();
  }
}
