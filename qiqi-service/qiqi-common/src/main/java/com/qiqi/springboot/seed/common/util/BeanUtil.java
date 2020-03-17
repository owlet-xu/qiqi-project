package com.qiqi.springboot.seed.common.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

/**
 * Bean操作工具类
 *
 * @author yezhiyang
 * @date 2018/8/27
 */
public class BeanUtil {

    private BeanUtil(){}

    /**
     * 获取对象中为null的所有属性名
     *
     * @param source 任意对象
     * @return 对象中所有为null的属性名数组
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper bw = new BeanWrapperImpl(source);
        return Stream.of(bw.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> bw.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}
