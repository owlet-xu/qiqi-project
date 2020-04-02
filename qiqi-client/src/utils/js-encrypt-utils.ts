import jsEncrypt from 'jsencrypt';
/* eslint-disable */
/* tslint:disable */
// @ts-ignore
const publicKey =
  'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCP0DWooqqmjw4jT0jZpLO22QexHRMGaMKXNas/TJHiQRat/32/qIN/ouJ5oLp7IlxTdRpu4b6moe12lxNqRkBrKUw4Qs3Vp7qZ+pYVjCIPeC05KvayTT3mAIAxRKvDdAHRt9t9iGLezy+ynMa7ipInHRcBnxx1w5AF9t4G+uFrcQIDAQAB';

const jse = new jsEncrypt();
jse.setPublicKey(publicKey);

/**
 * 加密
 * @param str
 */
const encrypt = (str: string): string => {
  return str ? jse.encrypt(str) : str;
};

/**
 * 解密
 * @param str
 */
const decrypt = (str: string): string => {
  return str ? jse.decrypt(str) : str;
};

export { encrypt, decrypt };
