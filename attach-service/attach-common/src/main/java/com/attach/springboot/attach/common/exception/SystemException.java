package com.attach.springboot.attach.common.exception;

/**
 * 自定义通用系统异常,表示服务端内部出现的相关异常.
 * @author yezhiyang
 * @date 2018/8/22
 */
public class SystemException extends RuntimeException {
    private final int code;

    public SystemException(ResultStatus status) {
        super(status.getMessage());
        this.code = status.getCode();
    }

    public int getCode() {
        return code;
    }
}
