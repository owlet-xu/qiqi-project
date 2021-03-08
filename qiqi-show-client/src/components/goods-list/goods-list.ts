import { Vue, Component } from 'vue-property-decorator';
import { AppModule } from '@/store/modules/app';
// models
import { SmsInfo } from '@/models/sms-info';
import { PageInfo } from '@/models/page-info';
// services
import MessageService from '@/api/message-service';
// componets
import CarouselMain from '../carousel-main/carousel-main.vue';

@Component({
  components: {
    CarouselMain
  }
})
export default class GoodsList extends Vue {}
