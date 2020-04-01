package com.qiqi.springboot.seed.bz1.controller;

import com.qiqi.springboot.seed.bz1.contract.model.MenuInfo;
import com.qiqi.springboot.seed.bz1.contract.service.MenuService;
import com.qiqi.springboot.seed.bz1.contract.service.RRoleMenuPrivilegeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-30 16:55
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Api(value = "/api/v1", tags = { "菜单权限模块Api"})
public class MenuPrivilegeController {

    @Autowired
    RRoleMenuPrivilegeService rRoleMenuPrivilegeService;

    @Autowired
    MenuService menuService;

    @ApiOperation(value = "查询启用的菜单和权限树", notes = "查询启用的菜单和权限树")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = Boolean.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/menu-privilege/tree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MenuInfo>> findMenuPrivelegeTree() {
        return ResponseEntity.status(HttpStatus.OK).body(rRoleMenuPrivilegeService.findMenuPrivelegeTree());
    }

    @ApiOperation(value = "查询角色的启用菜单权限列表", notes = "查询角色的启用菜单权限列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = MenuInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/menu-privilege/role/list/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MenuInfo>> findRoleMenuPrivelegeList(
            @ApiParam(value = "角色id", required = true)
            @PathVariable String roleId
    ) {
        String[] roleIds = {roleId};
        return ResponseEntity.status(HttpStatus.OK).body(rRoleMenuPrivilegeService.findRoleMenuPrivelegeList(Arrays.asList(roleIds)));
    }

    @ApiOperation(value = "查询所有菜单和权限树", notes = "查询所有菜单和权限树(包括禁用)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = Boolean.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/menu-privilege/tree/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MenuInfo>> findAllMenuPrivelegeTree() {
        return ResponseEntity.status(HttpStatus.OK).body(rRoleMenuPrivilegeService.findAllMenuPrivelegeTree());
    }

    @ApiOperation(value = "保存菜单", notes = "新增or更新菜单")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = MenuInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/menu-privilege/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> saveMenu(
            @ApiParam(value = "菜单model", required = true)
            @RequestBody @Valid MenuInfo menuInfo
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(menuService.save(menuInfo));
    }


    @ApiOperation(value = "删除", notes = "删除菜单，包括子菜单")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = Boolean.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/menu-privilege/remove/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> disableMenu(
            @ApiParam(value = "菜单id", required = true)
            @PathVariable String id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(menuService.disableMenu(id));
    }


    @ApiOperation(value = "添加菜单权限", notes = "新增or更新菜单")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = MenuInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/menu-privilege/privileges/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addMenuPrivileges(
            @ApiParam(value = "菜单model", required = true)
            @RequestBody MenuInfo menuInfo
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(menuService.addMenuPrivileges(menuInfo));
    }

    @ApiOperation(value = "删除菜单权限", notes = "新增or更新菜单")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = MenuInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/menu-privilege/privileges/remove", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> removeMenuPrivileges(
            @ApiParam(value = "菜单model", required = true)
            @RequestBody MenuInfo menuInfo
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(menuService.removeMenuPrivileges(menuInfo));
    }
}
