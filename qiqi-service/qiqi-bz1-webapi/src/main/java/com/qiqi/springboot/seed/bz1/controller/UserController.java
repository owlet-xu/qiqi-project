package com.qiqi.springboot.seed.bz1.controller;

import com.qiqi.springboot.seed.bz1.contract.model.PageInfo;
import com.qiqi.springboot.seed.bz1.contract.model.UserInfo;
import com.qiqi.springboot.seed.bz1.contract.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author xuguoyuan
 * @description
 * @date 2020/3/2
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Api(value = "/api/v1", tags = { "用户模块Api"})
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation(value = "查询所有用户（启用与禁用的）", notes = "查询所有用户（启用与禁用的）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = UserInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/user/list/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageInfo<UserInfo>> findUserListPage(
            @ApiParam(value = "分页查询model", required = true)
            @RequestBody PageInfo<UserInfo> pageInfo
    ) {
        PageInfo<UserInfo> result = userService.findUserListPage(pageInfo);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "保存用户", notes = "新增or更新用户")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = UserInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/user/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> saveUser(
            @ApiParam(value = "用户model", required = true)
            @RequestBody UserInfo userInfo
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveUser(userInfo));
    }

    @ApiOperation(value = "测试用户数据", notes = "测试用户数据")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = UserInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/user/test-add", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> testAdd() {
        for(int i = 0; i < 1000; i++) {
            UserInfo temp = new UserInfo();
            temp.setUserType("1");
            temp.setName("name" + i);
            temp.setUserName("username" + i);
            userService.saveUser(temp);
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = Boolean.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/user/remove/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> disableUser(
            @ApiParam(value = "用户id", required = true)
            @PathVariable String id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.disableUser(id));
    }
}
