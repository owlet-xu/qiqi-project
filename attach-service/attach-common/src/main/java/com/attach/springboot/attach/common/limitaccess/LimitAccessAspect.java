package com.attach.springboot.attach.common.limitaccess;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xuguoyuan
 * @Description //TODO
 * @createTime 2020-11-20 18:03:00
 **/
@Aspect
@Component
@Order
public class LimitAccessAspect {
    private static final Logger logger = LoggerFactory.getLogger(LimitAccessAspect.class);
    private Map<String, List<Long>> limitMap = new HashMap<>();

    @Pointcut("@annotation(limitAccess)")
    public void limitAccessPointCut(LimitAccess limitAccess) {
        // 限制接口调用切面类
    }

    @Around(value = "limitAccessPointCut(limitAccess)", argNames = "point,limitAccess")
    public Object doAround(ProceedingJoinPoint point, LimitAccess limitAccess) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != attributes) {
            String className = point.getTarget().getClass().getName();
            String methodName = point.getSignature().getName();
            HttpServletRequest request = attributes.getRequest();
            String key = className + "." + methodName + "#" + request.getSession().getId();
            List<Long> millisecondList = limitMap.get(key);
            long now = System.currentTimeMillis();
            if (null == millisecondList) {
                List<Long> list = new ArrayList<>();
                list.add(now);
                limitMap.put(key, list);
            } else {
                List<Long> newMillisecondList = new ArrayList<>(millisecondList.size());
                millisecondList.forEach(millisecond -> {
                    // 当前访问时间 - 历史访问时间 < 限制时间
                    if (now - millisecond < limitAccess.millisecond()) newMillisecondList.add(millisecond);
                });
                // 时间段内超过访问频次上限 - 阻断
                if (newMillisecondList.size() >= limitAccess.frequency()) {
                    logger.info("接口调用过于频繁 {}", key);
                    Map<String,String> res = new HashMap<>();
                    res.put("success","false");
                    res.put("message","接口调用过于频繁");
                    return ResponseEntity.status(HttpStatus.OK).body(res);
                }
                newMillisecondList.add(now);
                // 更新接口访问记录
                limitMap.put(key, newMillisecondList);
            }
        }
        return point.proceed();
    }
}
