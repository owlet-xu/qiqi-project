package com.attach.springboot.attach.backend.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Created by xiaodm on 2016/5/5.
 */
@Aspect
@Component
public class ControllerLogAspectJ {
    Logger logger = LoggerFactory.getLogger(ControllerLogAspectJ.class);


    //  前置通知：
    @Before("execution(public * com.attach.springboot.attach.bz1.controller..*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        StringBuilder args = new StringBuilder();
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            args.append(joinPoint.getArgs()[i] + ",");
        }
        logger.info("The method {} begin, Args:{}",joinPoint.getSignature().getName(), args);
    }

    /*最终通知（after advice）在连接点结束之后执行，不管返回结果还是抛出异常。*/
    @After("execution(public * com.attach.springboot.attach.bz1.controller..*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("The method {} end",joinPoint.getSignature().getName());
    }

    /*异常通知：仅当连接点抛出异常时执行。*/
    @AfterThrowing(pointcut = "execution(public * com.attach.springboot.attach.bz1.controller..*.*(..))", throwing = "throwable")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        logger.error("exception {} in method {}",throwable,joinPoint.getSignature().getName());
    }

}
