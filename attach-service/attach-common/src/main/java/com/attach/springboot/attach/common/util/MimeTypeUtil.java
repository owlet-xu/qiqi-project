package com.attach.springboot.attach.common.util;

import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

/**
 * 媒体类型操作工具类
 */
public class MimeTypeUtil {
    private MimeTypeUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 根据ContentType获取对应的文件扩展名
     *
     * @param contentType 文件内容类型
     * @return 扩展名字符串
     */
    public static String getExtensionByContentType(String contentType) {
        try {
            return MimeTypes.getDefaultMimeTypes().forName(contentType).getExtension();
        } catch (MimeTypeException e) {
            return "";
        }
    }


}
