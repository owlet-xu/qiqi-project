const getBase64Pic = (data: string) => {
  if (data) {
    const head = data.substring(0, 10);
    if (head === 'data:image') {
      return data;
    } else {
      return 'data:image/png;base64,' + data;
    }
  }
  return data;
};

const getPicBase64NoHead = (data: string) => {
  if (data) {
    const pos = data.indexOf(';base64,');
    if (pos === -1) {
      return data;
    } else {
      return data.substring(pos + 8, data.length);
    }
  }
  return data;
};

/**
 * 判断base64字符串是否超过指定大小 true 合格 false 不合格
 * @param base64Str
 * @param size 单位为k 例如 30 代表30k
 */
const isBase64Limit = (base64Str: string, limit: number) => {
  if (!base64Str) {
    return true;
  }
  base64Str = getPicBase64NoHead(base64Str);
  const length: any = base64Str.length - (base64Str.length / 8) * 2;
  const filelenth: any = parseInt(length, 10);
  return filelenth / 1024 < limit;
};

const compressBase64 = (base64Str: any, ratio: any) => {
  const promise = new Promise((resolve: any, reject: any) => {
    const image = new Image();
    image.src = base64Str;
    image.onload = () => {
      const canvas = document.createElement('canvas');
      const ctx: any = canvas.getContext('2d');
      const width = image.width;
      const height = image.height;
      canvas.width = width;
      canvas.height = height;
      ctx.drawImage(image, 0, 0, width, height);
      const dataUrl = canvas.toDataURL('image/jpeg', ratio);
      resolve(dataUrl);
    };
    image.onerror = () => {
      reject(new Error('Could not load image'));
    };
  });
  return promise;
};

/**
 * 压缩图片- 不断压缩，直到符合条件
 * @param base64Str 图片base64字符串，包括头信息
 * @param ratio 压缩率
 * @param limit 限制大小单位为k 例如 30 代表30k
 */
const compressBase64ForSize = async (base64Str: any, ratio: any, limit: number) => {
  if (isBase64Limit(base64Str, limit)) {
    return base64Str;
  }
  base64Str = await compressBase64(base64Str, ratio);
  while (!isBase64Limit(base64Str, limit)) {
    if (ratio - 0.2 >= 0) {
      ratio = ratio - 0.1;
    } else if ((ratio - 0.1 > 0 && ratio - 0.2 < 0) || ratio - 0.02 >= 0) {
      ratio = ratio - 0.01;
    } else {
      break;
    }
    base64Str = await compressBase64(base64Str, ratio);
  }
  return getBase64Pic(base64Str);
};

/**
 * 从上传文件插件中获取base64
 * @param file
 */
const getBase64FromFile = (file: any): Promise<any> => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    let imgResult: any = '';
    reader.readAsDataURL(file);
    reader.onload = () => {
      imgResult = reader.result;
    };
    reader.onerror = (error: any) => {
      reject(error);
    };
    reader.onloadend = () => {
      resolve(imgResult);
    };
  });
};

export { getBase64Pic, getPicBase64NoHead, compressBase64ForSize, getBase64FromFile };
