package com.qiqi.springboot.seed.common.util;

import com.qiqi.springboot.seed.common.exception.BusinessException;
import com.qiqi.springboot.seed.common.exception.ResultStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import java.util.Collection;
import java.util.Map;

/**
 * 校验工具类,统一校验,便于维护(例如修改Spring的StringUtils为Apache Commons的StringUtils)
 * @author yezhiyang
 * @date 2018/8/29
 */
public class CheckUtils {

    private CheckUtils(){}

    /**
     * 校验任意个字符串是否有空,如果有1个为空,则抛出自定义参数不完整异常
     * @param args 任意个字符串
     */
    public static void checkEmpty(String... args) {
        for (String str : args) {
            if (StringUtils.isEmpty(str)) {
                throw new BusinessException(ResultStatus.PARAM_NOT_COMPLETE);
            }
        }
    }

    /**
     * 校验对象是否为null,如果为null,则抛出自定义数据不存在异常
     * @param obj 任意对象
     */
    public static void checkExist(Object obj) {
        if (obj == null) {
            throw new BusinessException(ResultStatus.DATA_NOT_EXIST);
        }
    }

    /**
     * 根据表达式真假校验数据是否存在,如果表达式结果为false,则抛出自定义数据不存在异常
     * @param expression 条件表达式
     */
    public static void checkExist(boolean expression) {
        if (!expression) {
            throw new BusinessException(ResultStatus.DATA_NOT_EXIST);
        }
    }

    /**
     * 校验单列集合是否为null或空,如果为null或空,则抛出自定义数据未找到异常
     * @param collection 单列集合
     */
    public static void checkFound(Collection collection) {
        if (collection.isEmpty()) {
            throw new BusinessException(ResultStatus.DATA_NOT_FOUND);
        }
    }

    /**
     * 校验Map是否为null或空,如果为null或空,则抛出自定义数据未找到异常
     * @param map 双列集合
     */
    public static void checkFound(Map map) {
        if (map == null || map.size() == 0) {
            throw new BusinessException(ResultStatus.DATA_NOT_FOUND);
        }
    }

    /**
     * 校验spring validation结果,如果校验不通过,则抛出自定义参数无效异常
     * @param bindingResult 校验结果对象
     */
    public static void checkBindingResult(BindingResult bindingResult) {
        if (bindingResult != null && bindingResult.hasErrors()) {
            throw new BusinessException(ResultStatus.PARAM_IS_INVALID);
        }
    }
}
