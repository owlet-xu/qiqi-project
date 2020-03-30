package com.qiqi.springboot.seed.bz1.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 16:55
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Api(value = "/api/v1", tags = { "菜单权限模块Api"})
public class MenuPrivilegeController {
}
