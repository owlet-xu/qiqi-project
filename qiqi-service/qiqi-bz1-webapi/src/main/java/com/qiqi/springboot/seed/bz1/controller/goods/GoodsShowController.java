package com.qiqi.springboot.seed.bz1.controller.goods;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuguoyuan
 * @description 商城的展示服务
 * @date 2020-04-20 18:08
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Api(value = "/api/v1", tags = { "商城的展示服务Api"})
public class GoodsShowController {
}
