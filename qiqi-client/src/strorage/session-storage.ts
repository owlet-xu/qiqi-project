// ocalStorage 和 sessionStorage 属性允许在浏览器中存储 key/value 对的数据。
// sessionStorage 用于临时保存同一窗口(或标签页)的数据，在关闭窗口或标签页之后将会删除这些数据
import { SessionStorageKeys } from '@/common/constant/session-storage-keys';

const setItem = (key: string, value: string) => {
  sessionStorage.setItem(key, value);
};

const getItem = (key: string) => {
  sessionStorage.getItem(key);
};

const removeItem = (key: string) => {
  sessionStorage.removeItem(key);
};

const clear = () => {
  sessionStorage.clear();
};

const MySessionStorage = {
  setItem,
  getItem,
  removeItem,
  clear
};

export { SessionStorageKeys, MySessionStorage };
