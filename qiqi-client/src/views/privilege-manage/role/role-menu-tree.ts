import { Component, Vue } from 'vue-property-decorator';
// services
import MenuService from '@/api/menu-service';
// models
import { MenuInfo } from '@/models/menu-info';

@Component
export default class RoleMenuTree extends Vue {
  private menuTree: MenuInfo[] = [];

  created() {
    this.findMenuPrivelegeTree();
  }

  findMenuPrivelegeTree() {
    MenuService.findMenuPrivelegeTree().then((res: MenuInfo[]) => {
      this.menuTree = res;
    });
  }
}
