import { Component, Vue } from 'vue-property-decorator';
import { AppModule } from '@/store/modules/app';
import { UserModule } from '@/store/modules/user';
import Breadcrumb from '@/components/Breadcrumb/index.vue';
import ErrorLog from '@/components/ErrorLog/index.vue';
import Hamburger from '@/components/Hamburger/index.vue';
import HeaderSearch from '@/components/HeaderSearch/index.vue';
import LangSelect from '@/components/lang-select/index';
import Screenfull from '@/components/Screenfull/index.vue';
import SizeSelect from '@/components/SizeSelect/index.vue';

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
    SizeSelect
  }
})
export default class extends Vue {
  get sidebar() {
    return AppModule.sidebar;
  }

  get device() {
    return AppModule.device.toString();
  }

  get avatar() {
    // return UserModule.avatar
    return '';
  }

  private toggleSideBar() {
    AppModule.ToggleSideBar(false);
  }

  private async LogOut() {
    await UserModule.LogOut();
    this.$router.push(`/login?redirect=${this.$route.fullPath}`);
  }
}
