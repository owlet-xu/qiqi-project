package com.attach.springboot.attach.bz1.controller;

import com.attach.springboot.attach.bz1.contract.model.Base64FileInfo;
import com.attach.springboot.attach.bz1.contract.model.FileInfo;
import com.attach.springboot.attach.bz1.contract.service.AttachService;
import com.attach.springboot.attach.common.limitaccess.LimitAccess;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件系统
 * 数据库为mongodb
 * 文件可以存服务器
 */
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
    @LimitAccess(frequency = 5, millisecond = 1000*60*60*24*10)
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

    @ApiOperation(value = "根据fileId下载文件")
    @ApiResponses({
            @ApiResponse(code = 200, message = "下载成功"),
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @GetMapping("/attaches/{fileId}/download")
    public ResponseEntity<InputStreamResource> download(@PathVariable("fileId") String fileId) {
        return attachService.handleDownload(fileId, true);
    }

    @ApiOperation(value = "根据fileId和所属模块删除文件")
    @ApiResponses({
            @ApiResponse(code = 204, message = "删除成功"),
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @LimitAccess(frequency = 5, millisecond = 1000*60*60*24*10)
    @DeleteMapping("/attaches/{fileId}/metadata/{moduleName}")
    public ResponseEntity<Void> deleteByFileIdAndModule(@PathVariable("fileId") String fileId,
                                                        @PathVariable("moduleName") String module) {
        attachService.deleteByFileIdAndModule(fileId, module);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @ApiOperation(value = "根据所属模块和文件ID集合批量删除文件")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @PostMapping("/attaches/metadata/{moduleName}/batch-delete")
    public ResponseEntity<FileInfo> batchDeleteByModuleAndFileIds(@PathVariable("moduleName") String module,
                                                                  @RequestBody List<String> fileIds) {
        attachService.batchDeleteByModuleAndFileIds(module, fileIds);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    @ApiOperation(value = "根据fileId和所属模块查询文件")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @GetMapping("/attaches/{fileId}/metadata/{moduleName}")
    public ResponseEntity<FileInfo> findByFileIdAndModule(@PathVariable("fileId") String fileId,
                                                          @PathVariable("moduleName") String module) {
        FileInfo result = attachService.findByFileIdAndModule(fileId, module);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "根据所属模块查询文件集合")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @GetMapping("/attaches/metadata/module/{moduleName}")
    public ResponseEntity<List<FileInfo>> findByModule(@PathVariable("moduleName") String module) {
        return ResponseEntity.status(HttpStatus.OK).body(attachService.findByModule(module));
    }

    @ApiOperation(value = "根据系统查询文件集合")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @GetMapping("/attaches/metadata/system/{system}")
    public ResponseEntity<List<FileInfo>> findBySystem(@PathVariable("system") String system) {
        return ResponseEntity.status(HttpStatus.OK).body(attachService.findBySystem(system));
    }

    @ApiOperation(value = "根据id更新metadata数据")
    @ApiResponses({
            @ApiResponse(code = 200, message = "上传成功"),
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @PostMapping(value = "/attaches/metadata/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileInfo> updateMetadataById(@RequestBody FileInfo fileInfo) {
        return ResponseEntity.status(HttpStatus.OK).body(attachService.updateMetadataById(fileInfo));
    }

    @ApiOperation(value = "根据fileId预览视频")
    @ApiResponses({
            @ApiResponse(code = 200, message = "预览成功"),
            @ApiResponse(code = 400, message = "参数校验失败"),
            @ApiResponse(code = 500, message = "服务端异常")
    })
    @GetMapping("/attaches/{fileId}/video/preview")
    public void previewVideo(@PathVariable("fileId") String fileId, HttpServletRequest request, HttpServletResponse response) {
        attachService.previewVideo(response, fileId);
    }

}
