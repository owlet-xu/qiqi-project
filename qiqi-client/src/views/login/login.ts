import { Component, Vue, Watch } from 'vue-property-decorator';
import { Route } from 'vue-router';
import { Dictionary } from 'vue-router/types/router';
import { Form as ElForm, Input } from 'element-ui';
import { stringFormatArr } from '@/utils/string-utils';
import LoginService from '@/api/login-service';

import LangSelect from '@/components/lang-select/index.vue';

@Component({
  name: 'Login',
  components: {
    LangSelect
  }
})
export default class extends Vue {
  private loginForm = {
    username: '',
    password: ''
  };
  private loginRules = {
    username: [{ validator: this.validateUsername, trigger: 'blur' }],
    password: [{ validator: this.validatePassword, trigger: 'blur' }]
  };
  private passwordType: '' | 'password' = 'password';
  private loading = false;
  private capsTooltip = false;
  // 用户登录界面重定向
  private redirect?: string;
  private otherQuery: Dictionary<string> = {};

  mounted() {
    if (this.loginForm.username === '') {
      (this.$refs.username as Input).focus();
    } else if (this.loginForm.password === '') {
      (this.$refs.password as Input).focus();
    }
  }

  @Watch('$route', { immediate: true })
  private onRouteChange(route: Route) {
    // TODO: remove the "as Dictionary<string>" hack after v4 release for vue-router
    // See https://github.com/vuejs/vue-router/pull/2050 for details
    const query = route.query as Dictionary<string>;
    if (query) {
      this.redirect = query.redirect;
      this.otherQuery = this.getOtherQuery(query);
    }
  }

  validateUsername(rule: any, value: string, callback: any) {
    let tips = '';
    if (!value) {
      tips = stringFormatArr(this.$t('NotEmptyTip').toString(), [this.$t('Login.UserName').toString()]);
      callback(new Error(tips));
    } else if (value.length > 50) {
      tips = stringFormatArr(this.$t('MaxStringLenTip').toString(), [this.$t('Login.UserName').toString(), '50']);
      callback(new Error(tips));
    } else {
      callback();
    }
  }

  validatePassword(rule: any, value: string, callback: any) {
    let tips = '';
    if (!value) {
      tips = stringFormatArr(this.$t('NotEmptyTip').toString(), [this.$t('Login.Password').toString()]);
      callback(new Error(tips));
    } else if (value.length < 6) {
      tips = stringFormatArr(this.$t('MinStringLenTip').toString(), [this.$t('Login.Password').toString(), '6']);
      callback(new Error(tips));
    } else {
      callback();
    }
  }

  private checkCapslock(e: KeyboardEvent) {
    const { key } = e;
    this.capsTooltip = key !== null && key.length === 1 && (key >= 'A' && key <= 'Z');
  }

  private showPwd() {
    if (this.passwordType === 'password') {
      this.passwordType = '';
    } else {
      this.passwordType = 'password';
    }
    this.$nextTick(() => {
      (this.$refs.password as Input).focus();
    });
  }

  private validForm() {
    (this.$refs.loginForm as ElForm).validate((valid: boolean) => {
      if (valid) {
        this.handleLogin();
      }
    });
  }

  private handleLogin() {
    this.$router.push({
      path: this.redirect || '/',
      query: this.otherQuery
    });
    return;
    this.loading = true;
    LoginService.login(this.loginForm.username, this.loginForm.password).then((res: any) => {
      this.$router.push({
        path: this.redirect || '/',
        query: this.otherQuery
      });
    }).finally(() => {
      this.loading = false;
    });
  }

  private getOtherQuery(query: Dictionary<string>) {
    return Object.keys(query).reduce(
      (acc, cur) => {
        if (cur !== 'redirect') {
          acc[cur] = query[cur];
        }
        return acc;
      },
      {} as Dictionary<string>
    );
  }
}