/**
 * 配置文件初始化
 */
import { AppModule } from '@/store/modules/app';

const Config = async (): Promise<any> => {
  return AppModule.setConfigs();
};

export default Config;
