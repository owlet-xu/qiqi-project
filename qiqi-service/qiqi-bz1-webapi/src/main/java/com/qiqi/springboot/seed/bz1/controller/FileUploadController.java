package com.qiqi.springboot.seed.bz1.controller;

import com.qiqi.springboot.seed.bz1.contract.model.FileMeta;
import com.qiqi.springboot.seed.bz1.contract.service.FileUploadService;
import com.qiqi.springboot.seed.bz1.contract.service.LoginService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        FileMeta fileMeta = fileUploadService.upload(request, response, null);
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

    @ApiOperation(value = "删除无用图片", notes = "删除无用图片")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = Boolean.class),
            @ApiResponse(code = 400, message = "参数非法", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "服务器异常", response = ResponseEntity.class)
    })
    @RequestMapping(value = "/upload/remove", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> clearFiles() {
        return ResponseEntity.status(HttpStatus.OK).body(fileUploadService.clearFiles());
    }

}
