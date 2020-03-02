package com.qiqi.springboot.seed.common.exception;

/**
 * 自定义通用业务异常,表示不符合我们业务规则的相关异常.
 * @author yezhiyang
 * @date 2018/8/22
 */
public class BusinessException extends RuntimeException {
    private final int code;

    public BusinessException(ResultStatus status) {
        super(status.getMessage());
        this.code = status.getCode();
    }

    public int getCode() {
        return code;
    }
}