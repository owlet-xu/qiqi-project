package com.qiqi.springboot.seed.backend.aspectj;

import com.alibaba.fastjson.JSONObject;
import com.qiqi.springboot.seed.common.configs.XseedSettings;
import com.qiqi.springboot.seed.common.exception.BusinessException;
import com.qiqi.springboot.seed.common.exception.ResultStatus;
import com.qiqi.springboot.seed.common.redis.RedisUtil;
import com.qiqi.springboot.seed.common.util.HttpServletContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-18 15:23
 */
@Aspect
@Component
public class TokenVerificationAspect {

    @Value("${app.check-token-url}")
    private String CHECK_TOKEN_URL;

    @Value("${app.check-token}")
    private boolean IS_CHECK_TOKEN;

    @Value("${app.check-token-timeout}")
    private int checkTokenTimeout;


    private final String CLIENT_ID = "client_id";
    private final String ERROR = "error";
    private final String ERROR_DESCRIPTION = "error_description";

    private final Logger logger;

    private final RestTemplate restTemplate;

    private final RedisUtil redisUtil;

    public TokenVerificationAspect(RestTemplate restTemplate, RedisUtil redisUtil) {
        this.restTemplate = restTemplate;
        this.redisUtil = redisUtil;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Pointcut("execution(public * com.qiqi.springboot.seed.bz1.controller..*.*(..))")
    private void needVerify() {
        //共享切点配置
    }

    @Around(value = "needVerify()")
    public Object check(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (IS_CHECK_TOKEN) {
            checkToken();
        }
        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }

    private void checkToken() {
//        try {
//             ResponseEntity<String> response = restTemplate.getForEntity(CHECK_TOKEN_URL, String.class);
//             String data = response.getBody();
//            if (!StringUtils.isEmpty(data) && isCheckError(data)) {
//                String clientToken = HttpServletContextHolder.getHeader(XseedSettings.authorizationHeadName);
//                this.logger.warn("Check Token Failed, token: {}", clientToken);
//                this.logger.warn(data);
//                String errorMessage = extractErrorMessage(data);
//                throw new BusinessException(ResultStatus.TOKEN_CHECK_FAILED.getCode(), errorMessage);
//            }
//        } catch (RestClientException ex) {
//            String errorMessage = ex.getMessage();
//            throw new BusinessException(ResultStatus.TOKEN_CHECK_FAILED.getCode(), errorMessage);
//        }
        String url = HttpServletContextHolder.getCurrentRequest().getRequestURI();
        if (isIgloreUrl(url)) {
            return;
        }
        String clientToken = HttpServletContextHolder.getHeader(XseedSettings.authorizationHeadName);
        if (redisUtil.hasKey(clientToken)) {
            redisUtil.expire(clientToken, checkTokenTimeout);
        } else {
            String errorMessage = "Check Token Failed";
            this.logger.warn("Check Token Failed, token: {}", clientToken);
            throw new BusinessException(ResultStatus.TOKEN_CHECK_FAILED.getCode(), errorMessage);
        }
    }

    private boolean isIgloreUrl(String url) {
        // 登陆接口
        if (url.indexOf("/login") != -1) {
            return true;
        }
        // 预览图片接口
        if (url.indexOf("/upload/preview/") != -1) {
            return true;
        }
        return false;
    }

    private boolean isCheckError(String response) {
        return !response.contains(CLIENT_ID);
    }

    private String extractErrorMessage(String response) {
        JSONObject gson = JSONObject.parseObject(response);
        String error = gson.get(ERROR).toString();
        String errorDescription = gson.get(ERROR_DESCRIPTION).toString();
        return error + ", " + errorDescription;
    }
}
