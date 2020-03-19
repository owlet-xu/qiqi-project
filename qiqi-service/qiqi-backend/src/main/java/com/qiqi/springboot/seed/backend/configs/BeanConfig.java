package com.qiqi.springboot.seed.backend.configs;

import com.google.common.collect.Lists;
import com.qiqi.springboot.seed.common.interceptor.TokenInterceptor;
import com.qiqi.springboot.seed.common.model.AppConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-18 15:54
 */
@Configuration
public class BeanConfig {
    private final AppConfigurationProperties appConfigurationProperties;

    @Autowired
    public BeanConfig(AppConfigurationProperties appConfigurationProperties) {
        this.appConfigurationProperties = appConfigurationProperties;
    }

    @Bean
    public AppInit appInitBean() {
        return new AppInit();
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder
                .setConnectTimeout(this.appConfigurationProperties.getRestConnectTimeout())
                .setReadTimeout(this.appConfigurationProperties.getRestReadTimeout())
                .build();
        restTemplate.setInterceptors(Lists.newArrayList(new TokenInterceptor()));
        return restTemplate;
    }
}
