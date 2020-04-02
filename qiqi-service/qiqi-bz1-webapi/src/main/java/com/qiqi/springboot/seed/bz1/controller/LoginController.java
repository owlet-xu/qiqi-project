package com.qiqi.springboot.seed.bz1.controller;

import com.alibaba.fastjson.JSONObject;
import com.qiqi.springboot.seed.bz1.contract.model.LoginInfo;
import com.qiqi.springboot.seed.bz1.contract.model.UserInfo;
import com.qiqi.springboot.seed.bz1.contract.service.LoginService;
import com.qiqi.springboot.seed.common.util.MD5Utils;
import com.qiqi.springboot.seed.common.util.RSAUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    LoginService loginService;

    @ApiOperation(value = "登录", notes = "登录")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = UserInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginInfo> Login(@RequestBody JSONObject jsonObject) {
        String loginName =  jsonObject.get("loginName").toString();
        String password =  jsonObject.get("password").toString();
        String decrypt = RSAUtils.decryptByPrivate(password, RSAUtils.privateKey);
        String md5 = MD5Utils.MD5(decrypt);
        return ResponseEntity.status(HttpStatus.OK).body(loginService.login(loginName, md5));
    }
}
