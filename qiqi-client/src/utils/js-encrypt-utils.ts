import jsEncrypt from 'jsencrypt';
/* eslint-disable */
/* tslint:disable */
// @ts-ignore
// 用来加密-私钥在java
const publicKey = `MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCP0DWooqqmjw4jT0jZpLO22QexHRMGaMKXNas/TJHiQRat/32/qIN/ouJ5oLp7IlxTdRpu4b6moe12lxNqRkBrKUw4Qs3Vp7qZ+pYVjCIPeC05KvayTT3mAIAxRKvDdAHRt9t9iGLezy+ynMa7ipInHRcBnxx1w5AF9t4G+uFrcQIDAQAB`;
// 用来解密-公钥在java
const priveteKey = `MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIjSoMCSmoOxMe/E7EPxemCqZoMfd1p3yto4ywe9ysTAgWGdM95qVLbxTaIP0ZY+xSlmR4+vrnpof1A3NZLh6a9tEoXfJWHzCHrfGmz5NSxMqnbAyINv7Q6879yBGlkDfPdyfwiZzV/cdWwFLByaMDm6/ok8Ro30xJV3Z7WZ61ilAgMBAAECgYAw+DHQuqfSunjE+BYXuzWxo0tNYtW062sO74S7IJKSGNQ1u630zNnFIFhIyKCaxZyLVLAIHTi1VvE6JIhoKbMZK+stXGwKndX+YDMTg1J1T3ECTi2t480jnepLvkS/LUZk537rBrjCuZn7lQYRpqp1HeA3bYwZ/3ZFxC3F8J/3gQJBAPHZ5myF8ITdQC81noWFFqOs0G/BvmwRV7suLiT5jE9NzEj3Yiqs32cEVaS9FQCPFwM/OWkZOLsfEZuP7welQeMCQQCQ08NWZ9XoDOLIgAHlemFtMz2M7ET/uLV9PTXGeCPRz0aZ8lGBPyH2AGXXgoPLQ+6Wr1zyOMaa6Nz6XRQfTmHXAkALs7Jdf+GSXC412o9QAI+G3d67+ZDgI7qt+N8MtPJgB/Chey7Jx9GLZ39JWEjC3SmM46tAdZL0It20PDOhmBJnAkAjPiTuxd3b1ibpu5y6hwlmVW3I+UXhJMWlcFvBrEKrL9FybPFAjmhpQLeHtg70S9ene40WtJI/6CK+GiBnbd+JAkBOTL+uOSNmbgtVpGsCHo5OutA4fpxDJI8mEsRQZYVOCz93ttE6IFFA9DC5VtXVrhx2ZjklW0+JHyBQbLDWTDms`;
// 上面的公钥私钥不是一对的
const jse = new jsEncrypt();

/**
 * 加密
 * @param str
 */
const encrypt = (str: string): string => {
  jse.setPublicKey(publicKey);
  return str ? jse.encrypt(str) : str;
};

/**
 * 解密
 * @param str
 */
const decrypt = (str: string): string => {
  jse.setPrivateKey(priveteKey);
  return str ? jse.decrypt(str) : str;
};

export { encrypt, decrypt };
