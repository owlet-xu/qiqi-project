import { Vue, Component } from 'vue-property-decorator';
// components
import RoleMenuTree from './role/role-menu-tree';
import RoleList from './role/role-list';
// store
import { AppModule } from '@/store/modules/app';

@Component({
  components: {
    RoleMenuTree,
    RoleList
  }
})
export default class RoleManage extends Vue {
  get isMobile() {
    return AppModule.device.toString() === '1' ? false : true;
  }
}
