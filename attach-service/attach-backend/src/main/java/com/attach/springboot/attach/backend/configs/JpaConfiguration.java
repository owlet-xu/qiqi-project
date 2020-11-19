package com.attach.springboot.attach.backend.configs;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2017/3/3.
 */
@Configuration
// @EnableJpaRepositories(basePackages = "com.attach.springboot.attach")
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.attach.springboot.attach"})
public class JpaConfiguration {
}
