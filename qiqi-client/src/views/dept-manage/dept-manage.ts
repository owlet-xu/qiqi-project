import { Vue, Component } from 'vue-property-decorator';
// components
import DeptTree from './dept-tree';
import DeptUsers from './dept-users';
// store
import { AppModule } from '@/store/modules/app';

@Component({
  components: {
    DeptTree,
    DeptUsers
  }
})
export default class DeptManager extends Vue {
  get isMobile() {
    return AppModule.device.toString() === '1' ? false : true;
  }
}
