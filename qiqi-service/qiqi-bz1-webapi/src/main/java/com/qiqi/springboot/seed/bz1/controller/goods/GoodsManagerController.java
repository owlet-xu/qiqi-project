package com.qiqi.springboot.seed.bz1.controller.goods;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xuguoyuan
 * @description 商城的后台设置服务
 * @date 2020-04-20 18:09
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Api(value = "/api/v1", tags = { "商城的后台设置服务Api"})
public class GoodsManagerController {

    @ApiOperation(value = "测试", notes = "测试")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = Boolean.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/goods-manager/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> test() {
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}
