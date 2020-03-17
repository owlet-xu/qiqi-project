import { Vue, Component, Prop } from 'vue-property-decorator';
import { UserInfo } from '@/models/user-info';

@Component
export default class UserForm extends Vue {
    @Prop()
    userInfo!: UserInfo;

    get fromData() {
        return this.userInfo;
    }
}
