import { Vue, Component } from 'vue-property-decorator';
import { AppModule } from '@/store/modules/app';
// models
import { PageInfo } from '@/models/page-info';
// services
import MessageService from '@/api/message-service';
// componets
import CarouselMain from '../carousel-main/carousel-main.vue';
// route
import { RouterName } from '@/router/router-type';

@Component({
  components: {
    CarouselMain
  }
})
export default class HomePage extends Vue {

  handleMenu() {
    this.$router.push({ name: RouterName.GoodsList });
  }
}
