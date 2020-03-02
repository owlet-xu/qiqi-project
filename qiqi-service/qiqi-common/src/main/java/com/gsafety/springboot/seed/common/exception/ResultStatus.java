package com.qiqi.springboot.seed.common.exception;

/**
 * 统一的自定义异常返回状态枚举类,包含自定义错误码和自定义错误消息.
 * 错误码定义规则:A-BB-CC, A为1代表系统错误, 为2代表业务错误; BB代表各个服务模块(或子系统代号); CC代表错误序号,按顺序递增.
 * @author yezhiyang
 * @date 2018/8/22
 */
public enum ResultStatus {

    // 系统相关错误
    /**
     * 对于一些服务端事先预料到的异常,进行捕获时可以记录日志,然后抛出SystemException.
     */
    SYSTEM_INNER_ERROR(12001, "The system is busy, please try again later!"),
    /**
     * 一般指调用内部服务接口时发生的异常.
     */
    CALL_INNER_SERVICE_ERROR(12002, "Internal service call failed!"),
    /**
     * 一般指调用外部第三方接口时发生的异常.
     */
    CALL_OUTTER_SERVICE_ERROR(12003, "External service call failed!"),


    // 业务相关错误

    // 校验相关
    /**
     * 参数校验错误,一般是存数据的时候Hibernate Validator校验不通过.
     */
    PARAM_IS_INVALID(22001, "Parameter verification failed!"),
    /**
     * 一般指传递的参数通过StringUtils.isBlank()方法判断为真时可依情况抛出此错误.
     */
    PARAM_IS_BLANK(22002, "Parameter is blank!"),
    /**
     * 一般指SpingMVC进行参数绑定时类型匹配异常.
     */
    PARAM_TYPE_ERROR(22003, "Incorrect parameter type!"),
    /**
     * 一般指调用者传递的参数由SpringMVC进行对象绑定后,对象中某些成员变量值缺少.
     */
    PARAM_NOT_COMPLETE(22004, "Incomplete parameter!"),

    // 数据相关
    /**
     * 一般指根据某个字段查找单条记录时,该记录不存在.
     */
    DATA_NOT_EXIST(22005, "Data does not exist!"),
    /**
     * 一般指查找多条数据时,未找到任何数据.
     */
    DATA_NOT_FOUND(22006, "Data not found!"),
    /**
     * 一般指插入数据时,该记录已存在.
     */
    DATA_ALREADY_EXISTS(22007, "Data already exists!");

    private int code;
    private String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
