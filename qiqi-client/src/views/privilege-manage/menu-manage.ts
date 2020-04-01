import { Vue, Component } from 'vue-property-decorator';
// components
import MenuInfos from './menu/menu-infos';
import MenuTree from './menu/menu-tree';
// store
import { AppModule } from '@/store/modules/app';

@Component({
  components: {
    MenuInfos,
    MenuTree
  }
})
export default class MenuManage extends Vue {
  get isMobile() {
    return AppModule.device.toString() === '1' ? false : true;
  }
}
