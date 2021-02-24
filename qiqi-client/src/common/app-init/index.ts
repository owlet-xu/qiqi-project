
import Vue from 'vue';
import ElementUI from 'element-ui';
import PrivilegesMixin from '@/common/mixin/privileges-mixin'; // 权限管理
import SvgIcon from 'vue-svgicon';

export const initElementUi = () => {
    Vue.use(ElementUI);
};

export const initPrivileges = () => {
    Vue.mixin(PrivilegesMixin);
};

export const initSvgIcon = () => {
    Vue.use(SvgIcon, {
        tagName: 'svg-icon',
        defaultWidth: '1em',
        defaultHeight: '1em'
      });
};
