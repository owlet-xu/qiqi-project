import Vue from 'vue';
import 'mint-ui/lib/style.css';
import Animate from 'animate.css';
import {
    Button,
    Field,
    Toast,
    Header,
    Swipe, SwipeItem
} from 'mint-ui';

declare module 'vue/types/vue' {
    interface Vue {
        // 以下是在main.ts中挂载到Vue.prototype上的变量，放在shims-vue.d.ts中无效
        $toast: any;
    }
}
export const initMintUi = () => {
    Vue.component(Swipe.name, Swipe);
    Vue.component(SwipeItem.name, SwipeItem);
    Vue.component(Header.name, Header);
    Vue.component(Toast.name, Toast);
    Vue.component(Field.name, Field);
    Vue.component(Button.name, Button);
    Vue.prototype.$toast = Toast;
    Vue.use(Animate);
};
