package com.qiqi.springboot.seed.bz1.controller;

import com.qiqi.springboot.seed.bz1.contract.model.PrivilegeInfo;
import com.qiqi.springboot.seed.bz1.contract.service.PrivilegeService;
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
 * @date 2020-03-31 17:49
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Api(value = "/api/v1", tags = { "权限模块Api"})
public class PrivilegeController {

    @Autowired
    PrivilegeService privilegeService;

    @ApiOperation(value = "查询启用的菜单和权限树", notes = "查询启用的菜单和权限树")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = Boolean.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/privilege/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PrivilegeInfo>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(privilegeService.findAll());
    }


    @ApiOperation(value = "保存权限", notes = "新增or更新权限")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = PrivilegeInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/privilege/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> save(
            @ApiParam(value = "权限model", required = true)
            @RequestBody @Valid PrivilegeInfo privilegeInfo
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(privilegeService.save(privilegeInfo));
    }

    @ApiOperation(value = "删除权限", notes = "删除权限")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = Boolean.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/privilege/remove/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> disablePrivilege(
            @ApiParam(value = "权限id", required = true)
            @PathVariable String id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(privilegeService.enablePrivilege(id));
    }

    @ApiOperation(value = "获取菜单的启用的权限", notes = "不包括禁用的权限")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = Boolean.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/privilege/menu/privileges/{menuId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PrivilegeInfo>> getMenuPrivileges(
            @ApiParam(value = "菜单id", required = true)
            @PathVariable String menuId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(privilegeService.getMenuPrivileges(menuId));
    }

    @ApiOperation(value = "获取非菜单的启用的权限", notes = "不包括禁用的权限")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = Boolean.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/privilege/menu/privileges/other/{menuId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PrivilegeInfo>> getOtherMenuPrivileges(
            @ApiParam(value = "菜单id", required = true)
            @PathVariable String menuId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(privilegeService.getOtherMenuPrivileges(menuId));
    }
}
