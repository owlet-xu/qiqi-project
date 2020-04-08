package com.qiqi.springboot.seed.bz1.controller;

import com.alibaba.fastjson.JSONObject;
import com.qiqi.springboot.seed.bz1.contract.model.LoginInfo;
import com.qiqi.springboot.seed.bz1.contract.model.UserInfo;
import com.qiqi.springboot.seed.bz1.contract.service.LoginService;
import com.qiqi.springboot.seed.common.util.MD5Utils;
import com.qiqi.springboot.seed.common.util.RSAUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
        // 解密密码
        String decrypt = RSAUtils.decryptByPrivate(password, RSAUtils.privateKey);
        String md5 = MD5Utils.MD5(decrypt);
        LoginInfo res = loginService.login(loginName, md5);
        // 加密admin
        if (!StringUtils.isEmpty(res.getAdmin())) {
            res.setAdmin(RSAUtils.encryptByPublic(res.getAdmin() , RSAUtils.jsPublicKey));
        }
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @ApiOperation(value = "验证用户密码是否正确", notes = "验证用户密码是否正确")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = UserInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/login/valid/password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> validePassword(@RequestBody JSONObject jsonObject) {
        String token =  jsonObject.get("token").toString();
        String password =  jsonObject.get("password").toString();
        // 解密token和密码
        String tokenDe = RSAUtils.decryptByPrivate(token, RSAUtils.privateKey);
        String passwordDe = RSAUtils.decryptByPrivate(password, RSAUtils.privateKey);
        String passwordMd5 = MD5Utils.MD5(passwordDe);
        return ResponseEntity.status(HttpStatus.OK).body(loginService.validePassword(tokenDe, passwordMd5));
    }

    @ApiOperation(value = "设置用户新密码", notes = "设置用户新密码")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = UserInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/login/new/password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> newPassword(@RequestBody JSONObject jsonObject) {
        String token =  jsonObject.get("token").toString();
        String password =  jsonObject.get("password").toString();
        // 解密token和密码
        String tokenDe = RSAUtils.decryptByPrivate(token, RSAUtils.privateKey);
        String passwordDe = RSAUtils.decryptByPrivate(password, RSAUtils.privateKey);
        String passwordMd5 = MD5Utils.MD5(passwordDe);
        return ResponseEntity.status(HttpStatus.OK).body(loginService.newPassword(tokenDe, passwordMd5));
    }

    @ApiOperation(value = "重置用户密码", notes = "重置用户密码")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = UserInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/login/reset/password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> resetPassword(@RequestBody JSONObject jsonObject) {
        String token =  jsonObject.get("token").toString();
        String tokenDe = RSAUtils.decryptByPrivate(token, RSAUtils.privateKey);
        return ResponseEntity.status(HttpStatus.OK).body(loginService.resetPassword(tokenDe));
    }

    @ApiOperation(value = "验证超级管理员", notes = "验证超级管理员")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = UserInfo.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/login/check/admin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkAdmin(@RequestBody JSONObject jsonObject) {
        String token =  jsonObject.get("token").toString();
        String tokenDe = RSAUtils.decryptByPrivate(token, RSAUtils.privateKey);
        return ResponseEntity.status(HttpStatus.OK).body(loginService.checkAdmin(tokenDe));
    }

}
