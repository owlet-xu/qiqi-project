import { Component, Vue, Watch } from 'vue-property-decorator';
import { Getter, Action } from 'vuex-class';
import { AppModule } from '@/store/modules-store/app';
@Component({
  name: 'LangSelect'
})
export default class extends Vue {
  get language() {
    return  AppModule.language;
  }

  private handleSetLanguage(lang: string) {
    this.$i18n.locale = lang;
    AppModule.setLanguage(lang);
    this.$message({
      message: 'Switch Language Success',
      type: 'success'
    });
  }
}
