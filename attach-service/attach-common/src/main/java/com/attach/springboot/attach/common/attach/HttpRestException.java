package com.attach.springboot.attach.common.citysafety;

import org.springframework.http.HttpStatus;

/**
 * Created by administrator on 2016/10/26.
 * define HttpRestException class
 */
public class HttpRestException extends RestException{

    /**
     * Instantiates a new Model rest exception.
     *
     * @param httpStatus the http status
     * @param message    the message
     */
    public HttpRestException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

}
