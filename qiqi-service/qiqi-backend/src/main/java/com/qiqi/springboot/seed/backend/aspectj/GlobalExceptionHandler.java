package com.qiqi.springboot.seed.backend.aspectj;


import com.qiqi.springboot.seed.common.citysafety.HttpError;
import com.qiqi.springboot.seed.common.citysafety.RestException;
import com.qiqi.springboot.seed.common.exception.BusinessException;
import com.qiqi.springboot.seed.common.exception.ResultStatus;
import com.qiqi.springboot.seed.common.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by gaoqiang on 2016/10/27.
 * Extend the Exception Handler
 */

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * The Logger.
     */
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private HttpServletResponse response;

    /**
     * 用来统一处理自定义业务异常、SpringMVC参数绑定类型匹配异常、
     * http请求参数转换异常、spring data删除数据不存在异常,
     * 返回状态码为400,返回数据包含自定义errorCode和message.
     *
     * @param ex 异常对象，该异常一般是API调用者造成的.
     * @return HttpError
     */
    @ExceptionHandler({ BusinessException.class, TypeMismatchException.class,
            HttpMessageNotReadableException.class, EmptyResultDataAccessException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpError handleBadRequest(final Exception ex) {
        logger.warn(ex.getMessage(), ex);

        // 例如发生参数类型转换异常时会进入此分支
        if (ex instanceof TypeMismatchException) {
            return new HttpError(ResultStatus.PARAM_TYPE_ERROR.getCode(), ResultStatus.PARAM_TYPE_ERROR.getMessage());
        }

        // 例如发生json参数解析异常时会进入此分支
        if (ex instanceof HttpMessageNotReadableException) {
            return new HttpError(ResultStatus.PARAM_IS_INVALID.getCode(), ResultStatus.PARAM_IS_INVALID.getMessage());
        }

        // 例如删除时指定ID的记录不存在
        if (ex instanceof EmptyResultDataAccessException) {
            return new HttpError(ResultStatus.DATA_NOT_EXIST.getCode(), ResultStatus.DATA_NOT_EXIST.getMessage());
        }

        return new HttpError(((BusinessException) ex).getCode(), ex.getMessage());
    }

    /**
     * 用来统一处理自定义系统异常,返回状态码为500,返回数据包含自定义errorCode和message.
     *
     * @param ex 自定义系统异常,该异常一般是服务端内部发生错误.
     * @return HttpError
     */
    @ExceptionHandler(SystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpError handleSystemException(final SystemException ex) {
        logger.error(ex.getMessage(), ex);
        return new HttpError(ex.getCode(), ex.getMessage());
    }

    /**
     * Handle self defined exceptions http error.
     *
     * @param restException the rest exception
     * @return the http error
     */
    @ExceptionHandler(RestException.class)
    public HttpError handleSelfDefinedExceptions(RestException restException) {
        logger.error(restException.getMessage(), restException);
        response.setStatus(restException.getHttpStatus().value());
        return new HttpError(restException.getHttpStatus().value(),
                restException.getMessage());
    }

    /**
     * Handle un catch exceptions http error.
     *
     * @param exception the exception
     * @return the http error
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpError handleUnCatchExceptions(Exception exception) {
        logger.error(exception.getMessage(), exception);
        return new HttpError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected internal server error occurred!");
    }
}
