package com.qiqi.springboot.seed.bz1.controller;

import com.alibaba.fastjson.JSONObject;
import com.qiqi.springboot.seed.bz1.contract.model.LoginInfo;
import com.qiqi.springboot.seed.bz1.contract.model.UserInfo;
import com.qiqi.springboot.seed.bz1.contract.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuguoyuan
 * @description
 * @date 2020/3/15
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Api(value = "/api/v1", tags = { "登录模块Api"})
public class LoginController {
    @Autowired
    UserService userService;

    @ApiOperation(value = "保存用户", notes = "新增or更新用户")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = UserInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginInfo> Login(@RequestBody JSONObject jsonObject) {
        String loginName =  jsonObject.get("loginName").toString();
        String password =  jsonObject.get("password").toString();
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginName, password));
    }
}
