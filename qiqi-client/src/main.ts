import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import ElementUI from 'element-ui';
import SvgIcon from 'vue-svgicon';
import config from './utils/appconfig';
import i18n from '@/lang';
import 'element-ui/lib/theme-chalk/index.css';
import '@/assets/fonts/iconfont';
import '@/assets/styles/index.scss';
import '@/icons';

config(store).then(() => {
  Vue.use(ElementUI);
  Vue.use(SvgIcon, {
    tagName: 'svg-icon',
    defaultWidth: '1em',
    defaultHeight: '1em'
  });
  router.afterEach(() => {});

  new Vue({
    router,
    store,
    i18n,
    render: (h: any) => h(App)
  }).$mount('#app');
});
