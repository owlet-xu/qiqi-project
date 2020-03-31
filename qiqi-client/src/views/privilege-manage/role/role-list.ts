import { Vue, Component } from 'vue-property-decorator';

@Component
export default class RoleList extends Vue {
  private showEditDialog = false;
  private saving = false;
  private defaultProps = { children: 'children', label: 'name' };
  private search = '';
  private showAll = false;

  add(){}
}
