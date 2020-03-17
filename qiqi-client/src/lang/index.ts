import Vue from 'vue';
import VueI18n from 'vue-i18n';
import { CookiesKeys, Cookies } from '@/strorage/cookies';

import zh from './zh-CN.json';
import en from './en-US.json';
import es from './es-EC.json';

import ElementLocale from 'element-ui/lib/locale';
import elementEnLocale from 'element-ui/lib/locale/lang/en'; // element-ui lang
import elementZhLocale from 'element-ui/lib/locale/lang/zh-CN'; // element-ui lang
import elementEsLocale from 'element-ui/lib/locale/lang/es'; // element-ui lang

Vue.use(VueI18n);

const messages = {
  en: {
    ...en,
    ...elementEnLocale
  },
  zh: {
    ...zh,
    ...elementZhLocale
  },
  es: {
    ...es,
    ...elementEsLocale
  }
};

const i18n = new VueI18n({
  locale: Cookies.get(CookiesKeys.language) || 'zh',
  messages
});
// 解决Element-ui组件内的词条
ElementLocale.use(i18n.locale);
ElementLocale.i18n((key: any, value: any) => i18n.t(key, value));

export default i18n;
