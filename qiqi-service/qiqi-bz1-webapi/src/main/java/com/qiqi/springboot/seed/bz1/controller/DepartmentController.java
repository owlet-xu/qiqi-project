package com.qiqi.springboot.seed.bz1.controller;

import com.qiqi.springboot.seed.bz1.contract.model.DepartmentInfo;
import com.qiqi.springboot.seed.bz1.contract.model.PageInfo;
import com.qiqi.springboot.seed.bz1.contract.service.DepartmentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-23 09:08
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Api(value = "/api/v1", tags = { "部门模块Api"})
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @ApiOperation(value = "查询所有部门（启用与禁用的）", notes = "查询所有部门（启用与禁用的）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = DepartmentInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/department/list/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageInfo<DepartmentInfo>> findDepartmentListPage(
            @ApiParam(value = "分页查询model", required = true)
            @RequestBody PageInfo<DepartmentInfo> pageInfo
    ) {
        PageInfo<DepartmentInfo> result = departmentService.findDepartmentListPage(pageInfo);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "查询所有部门树（启用与禁用的）", notes = "查询所有部门树（启用与禁用的）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = DepartmentInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/department/tree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DepartmentInfo>> findDepartmentTree(
            @ApiParam(value = "查询model", required = true)
            @RequestBody DepartmentInfo departmentInfo
    ) {
        List<DepartmentInfo> result = departmentService.findDepartmentTree(departmentInfo);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "保存部门", notes = "新增or更新用户")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = DepartmentInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/department/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> saveDepartment(
            @ApiParam(value = "部门model", required = true)
            @RequestBody DepartmentInfo departmentInfo
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.saveDepartment(departmentInfo));
    }


    @ApiOperation(value = "删除", notes = "删除部门，包括子部门")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = Boolean.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/department/remove/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> disableDepartment(
            @ApiParam(value = "部门id", required = true)
            @PathVariable String id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.disableDepartment(id));
    }
}
