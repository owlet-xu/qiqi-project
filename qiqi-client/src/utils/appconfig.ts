/**
 * 配置文件初始化
 */
import axios from 'axios';
import { AppModule } from '@/store/modules-store/app';
// const isDev = process.env.NODE_ENV === 'development';
const configPath = '/config.json';

const Config = async (): Promise<any> => {
  const configs = await axios.get(configPath);
  return AppModule.setConfigs(configs.data);
};

export default Config;
