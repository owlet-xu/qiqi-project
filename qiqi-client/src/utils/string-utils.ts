import i18n from '@/lang';
/**
 * 截取字符串
 * @param str
 * @param len
 */
const subLongStr = (str: string, len: number) => {
  if (str && str.length > len) {
    str = str.slice(0, len) + '...';
  }
  return str;
};
/**
 * 返回 -1 0 1
 * @param a
 * @param b
 */
const sortStr = (a: string | undefined, b: string | undefined): number => {
  if (a && b) {
    if (i18n.locale && i18n.locale === 'zh-CN') {
      return a.localeCompare(b, 'zh');
    } else {
      return a.localeCompare(b);
    }
  } else if (!a && b) {
    return -1;
  } else if (a && !b) {
    return 1;
  } else {
    return 0;
  }
};

/**
 * 替换字符串
 * @param urlPath
 * @param params
 */
const stringFormatArr = (urlPath: string, params: string[]): string => {
  if (!urlPath || !params) {
    return urlPath;
  }
  params.forEach((param: string, index: number) => {
    urlPath = urlPath.replace(/\{[a-z,A-Z,0-9]+\}/, param);
  });
  return urlPath;
};

export { subLongStr, sortStr, stringFormatArr };
