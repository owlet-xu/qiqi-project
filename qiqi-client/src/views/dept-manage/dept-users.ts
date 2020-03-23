import { Vue, Component } from 'vue-property-decorator';
// tools
import { rxevent } from '@/utils/rxevent';

@Component
export default class DeptUsers extends Vue {
    private a = '';
    created() {
        rxevent.subscribe('aaabbb', 'DeptUsers', (res: any) => {
            this.a = res;
        });
    }
}
