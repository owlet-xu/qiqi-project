import { Component } from 'vue-property-decorator';
import { Action } from 'vuex-class';
import { mixins } from 'vue-class-component';
import { DeviceType } from '@/models/enums/device-type';
import { SettingsModule } from '@/store/modules/settings';
import { AppModule } from '@/store/modules/app';
import { AppMain, Navbar, Settings, Sidebar, TagsView } from './components';
import RightPanel from '@/components/RightPanel/index.vue';
import ResizeMixin from './mixin/resize';

@Component({
  name: 'Layout',
  components: {
    AppMain,
    Navbar,
    RightPanel,
    Settings,
    Sidebar,
    TagsView
  }
})
export default class extends mixins(ResizeMixin) {
  get classObj() {
    return {
      hideSidebar: !this.sidebar.opened,
      openSidebar: this.sidebar.opened,
      withoutAnimation: this.sidebar.withoutAnimation,
      mobile: this.device === DeviceType.Mobile
    };
  }

  get showSettings() {
    return SettingsModule.showSettings;
  }

  get showTagsView() {
    return SettingsModule.showTagsView;
  }

  get fixedHeader() {
    return SettingsModule.fixedHeader;
  }

  private handleClickOutside() {
    AppModule.CloseSideBar(false);
  }
}
