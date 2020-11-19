package com.attach.springboot.attach.bz1.controller;

import com.attach.springboot.attach.bz1.contract.model.Base64FileInfo;
import com.attach.springboot.attach.bz1.contract.model.FileInfo;
import com.attach.springboot.attach.bz1.contract.service.AttachService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(value = "/api/v1", description = "AttachController Api")   // NOSONAR
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class AttachController {

    @Autowired
    private AttachService attachService;

    @ApiOperation(value = "上传单个附件信息(metadata参考:{\"system\":\"\",\"module\":\"\",\"businessId\":\"\"})")
    @ApiResponses({
            @ApiResponse(code = 200, message = "上传成功"),
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @PostMapping(value = "/attaches/upload/single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<FileInfo>> upload(@RequestPart("file") MultipartFile file,
                                                 @RequestParam(value = "metadata", required = false) String metadata) {
        MultipartFile[] files = {file};
        List<FileInfo> fileInfos = attachService.batchUpload(files, metadata);
        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @ApiOperation(value = "上传base64文件字符串(metadata参考:{\"system\":\"\",\"module\":\"\",\"businessId\":\"\"})")
    @ApiResponses({
            @ApiResponse(code = 200, message = "上传成功"),
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @PostMapping("/attaches/upload/base64")
    public ResponseEntity<FileInfo> uploadFileString(@RequestBody Base64FileInfo base64FileInfo) {
        FileInfo fileInfo = attachService.uploadFileString(base64FileInfo);
        return ResponseEntity.status(HttpStatus.OK).body(fileInfo);
    }

    @ApiOperation(value = "根据fileId预览文件")
    @ApiResponses({
            @ApiResponse(code = 200, message = "预览成功"),
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @GetMapping("/attaches/{fileId}/preview")
    public ResponseEntity<InputStreamResource> preview(@PathVariable("fileId") String fileId) {
        return attachService.handleDownload(fileId, false);
    }

}