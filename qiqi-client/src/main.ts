import Vue from 'vue';
import App from './App.vue';
import store from '@/store';
import config from './utils/appconfig';
import i18n from '@/lang';
import router from '@/router';
import '@/venders/http-client';
import 'element-ui/lib/theme-chalk/index.css';
import '@/assets/fonts/iconfont';
import '@/assets/styles/index.scss';
import '@/icons';
import { initPrivileges, initElementUi, initSvgIcon } from '@/common/app-init';

config().then(() => {
  initElementUi();
  initSvgIcon();
  initPrivileges();
  new Vue({
    router,
    store,
    i18n,
    render: (h: any) => h(App)
  }).$mount('#app');
});
