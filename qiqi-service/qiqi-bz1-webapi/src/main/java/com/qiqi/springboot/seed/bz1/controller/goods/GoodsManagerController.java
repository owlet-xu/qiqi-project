package com.qiqi.springboot.seed.bz1.controller.goods;

import com.qiqi.springboot.seed.bz1.contract.model.goods.GoodsInfo;
import com.qiqi.springboot.seed.bz1.contract.service.goods.GoodsManagerService;
import com.qiqi.springboot.seed.bz1.contract.service.goods.GoodsQueryService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xuguoyuan
 * @description 商城的后台设置服务,面向管理员
 * @date 2020-04-20 18:09
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Api(value = "/api/v1", tags = { "商城的后台设置服务Api"})
public class GoodsManagerController {

    @Autowired
    GoodsQueryService goodsQueryService;
    @Autowired
    GoodsManagerService goodsManagerService;

    @ApiOperation(value = "保存商品", notes = "新增or更新商品")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = GoodsInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/goods-manager/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> saveGoods(
            @ApiParam(value = "商品model", required = true)
            @RequestBody @Valid GoodsInfo goodsInfo
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(goodsManagerService.saveGoods(goodsInfo));
    }

    @ApiOperation(value = "删除商品(逻辑删除)", notes = "删除商品(逻辑删除)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = Boolean.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/goods-manager/remove/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> disableGoods(
            @ApiParam(value = "商品id", required = true)
            @PathVariable String id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(goodsManagerService.disableGoods(id));
    }
}
