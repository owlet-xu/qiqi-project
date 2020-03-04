import { Component, Vue, Watch } from 'vue-property-decorator';
import { Getter, Action } from 'vuex-class';
import { AppTypes } from '@/store/types/app-types';
@Component({
  name: 'LangSelect'
})
export default class extends Vue {
  @Getter(AppTypes.getters.LANGUAGE)
  language!: string;
  @Action(AppTypes.actions.SET_LANGUAGE)
  setLanguage!: (lang: string) => void;

  private handleSetLanguage(lang: string) {
    this.$i18n.locale = lang;
    this.setLanguage(lang);
    this.$message({
      message: 'Switch Language Success',
      type: 'success'
    });
  }
}
