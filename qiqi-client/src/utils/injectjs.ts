import axios from 'axios';

/**
 * 通过路径获取注入js
 * @param path 在public文件夹下面的资源 例如 /config.json
 */
const getInjectjs = async (path: string) => {
  const res: any = await axios.get(path);
  if (res.status === 200) {
    return res.data;
  } else {
    return undefined;
  }
};

export { getInjectjs };
