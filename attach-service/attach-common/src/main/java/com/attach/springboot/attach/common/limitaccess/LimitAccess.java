package com.attach.springboot.attach.common.limitaccess;

import java.lang.annotation.*;

/**
 * @Author xuguoyuan
 * @Description //TODO
 * @createTime 2020-11-20 18:02:00
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitAccess {
    // 访问频率
    int frequency() default 10;

    // 时间段内（毫秒）
    long millisecond() default 1000;
}
