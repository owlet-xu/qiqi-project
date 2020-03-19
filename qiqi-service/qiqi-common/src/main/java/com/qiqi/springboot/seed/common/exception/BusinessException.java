package com.qiqi.springboot.seed.common.exception;

/**
 * 自定义通用业务异常,表示不符合我们业务规则的相关异常.
 * @author yezhiyang
 * @date 2018/8/22
 */
public class BusinessException extends RuntimeException {
    private final int code;
    private final String message;

    public BusinessException(ResultStatus status) {
        super(status.getMessage());
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {return message; }
}