package com.attach.springboot.attach.common.interceptor;

import com.attach.springboot.attach.common.configs.XseedSettings;
import com.attach.springboot.attach.common.util.HttpServletContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author xuguoyuan
 * @description 请求的头加上token
 * @date 2020-03-18 15:58
 */
public class TokenInterceptor implements ClientHttpRequestInterceptor{
    private static final String AUTHORIZATION = XseedSettings.authorizationHeadName;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();

        String tokenHeader = headers.getFirst(AUTHORIZATION);
        if (StringUtils.isEmpty(tokenHeader)) {
            String clientToken = HttpServletContextHolder.getHeader(AUTHORIZATION);
            if (!StringUtils.isEmpty(clientToken)) {
                headers.add(AUTHORIZATION, clientToken);
            }
        }
        return execution.execute(request, body);
    }
}
