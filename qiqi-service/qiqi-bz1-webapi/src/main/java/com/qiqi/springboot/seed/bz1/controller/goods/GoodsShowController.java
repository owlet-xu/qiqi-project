package com.qiqi.springboot.seed.bz1.controller.goods;

import com.qiqi.springboot.seed.bz1.contract.model.PageInfo;
import com.qiqi.springboot.seed.bz1.contract.model.goods.GoodsInfo;
import com.qiqi.springboot.seed.bz1.contract.service.goods.GoodsQueryService;
import com.qiqi.springboot.seed.bz1.contract.service.goods.OrdersManagerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xuguoyuan
 * @description 商城的展示服务，面向用户
 * @date 2020-04-20 18:08
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Api(value = "/api/v1", tags = { "商城的展示服务Api"})
public class GoodsShowController {
    @Autowired
    GoodsQueryService goodsQueryService;
    @Autowired
    OrdersManagerService ordersManagerService;

    @ApiOperation(value = "查询所有启用商品", notes = "查询所有启用商品")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = GoodsInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/goods-show/list/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageInfo<GoodsInfo>> findGoodsListPage(
            @ApiParam(value = "分页查询model", required = true)
            @RequestBody PageInfo<GoodsInfo> pageInfo
    ) {
        PageInfo<GoodsInfo> result = goodsQueryService.findGoodsListPage(pageInfo);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "获取商品详情", notes = "获取商品详情")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = Boolean.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/goods-show/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GoodsInfo> findGoodsById(
            @ApiParam(value = "商品id", required = true)
            @PathVariable String id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(goodsQueryService.findGoodsById(id));
    }
}
