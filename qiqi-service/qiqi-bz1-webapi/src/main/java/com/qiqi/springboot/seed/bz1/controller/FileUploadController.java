package com.qiqi.springboot.seed.bz1.controller;

import com.qiqi.springboot.seed.bz1.contract.model.FileMeta;
import com.qiqi.springboot.seed.bz1.contract.service.FileUploadService;
import com.qiqi.springboot.seed.bz1.contract.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-19 16:13
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Api(value = "/api/v1", tags = { "文件上传模块Api"})
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    /**
     * 用户头像上传
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/upload/head", method = RequestMethod.POST)
    public @ResponseBody
    FileMeta  uploadHeadImg(MultipartHttpServletRequest request, HttpServletResponse response) {
        FileMeta fileMeta = fileUploadService.uploadHeadImg(request, response);
        return fileMeta;
    }

    @ApiOperation(value = "根据fileId预览文件")
    @ApiResponses({
            @ApiResponse(code = 200, message = "预览成功"),
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @GetMapping("/upload/preview/{fileId}")
    public ResponseEntity<InputStreamResource> preview(@PathVariable("fileId") String fileId) {
        return fileUploadService.handleDownload(fileId, false);
    }

}
