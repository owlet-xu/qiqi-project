package com.qiqi.springboot.seed.bz1.contract.service;

import com.qiqi.springboot.seed.bz1.contract.model.FileMeta;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-19 16:19
 */
public interface FileUploadService {

    /**
     * 上传文件
     * @param request
     * @param response
     * @param type
     * @return
     */
    FileMeta upload(MultipartHttpServletRequest request, HttpServletResponse response, String type);

    /**
     * 处理下载或者预览请求
     * @param fileId
     * @param isDownload
     * @return
     */
    ResponseEntity<InputStreamResource> handleDownload(String fileId, boolean isDownload);
}
