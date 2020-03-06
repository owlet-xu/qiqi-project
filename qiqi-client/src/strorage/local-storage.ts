// 如果你想在浏览器窗口关闭后还保留数据，可以使用 localStorage 属性
// 该数据对象没有过期时间，今天、下周、明年都能用，除非你手动去删除。
import { LocalStorageKeys } from '@/common/constant/local-storage-keys';

const setItem = (key: string, value: string) => {
  localStorage.setItem(key, value);
};

const getItem = (key: string) => {
  localStorage.getItem(key);
};

const removeItem = (key: string) => {
  localStorage.removeItem(key);
};

const clear = () => {
  localStorage.clear();
};

const setObject = (key: string, value: any) => {
  const str = value ? JSON.stringify(value) : '';
  localStorage.setItem(key, str);
};

const getObject = (key: string) => {
  const str = localStorage.getItem(key);
  return str ? JSON.parse(str) : {};
};

const MylocalStorage = {
  setItem,
  getItem,
  removeItem,
  clear,
  setObject,
  getObject
};

export { LocalStorageKeys, MylocalStorage };
