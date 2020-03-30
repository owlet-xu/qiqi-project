package com.qiqi.springboot.seed.bz1.controller;

import com.qiqi.springboot.seed.bz1.contract.model.RoleInfo;
import com.qiqi.springboot.seed.bz1.contract.service.RoleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 16:55
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Api(value = "/api/v1", tags = { "角色模块Api"})
public class RoleController {

    @Autowired
    RoleService roleService;

    @ApiOperation(value = "查询所有角色", notes = "携带菜单权限数据")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = Boolean.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/role/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoleInfo>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.findList(new RoleInfo()));
    }

    @ApiOperation(value = "保存角色", notes = "新增or更新角色")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = RoleInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/role/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> saveUser(
            @ApiParam(value = "角色model", required = true)
            @RequestBody @Valid RoleInfo roleInfo
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.save(roleInfo));
    }
}
