package com.attach.springboot.attach.common.util;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-23 13:54
 */
public class RepositoryUtils {
    public static String prefixForLike(String val) {
        return "%" + val+ "%";
    }
}
