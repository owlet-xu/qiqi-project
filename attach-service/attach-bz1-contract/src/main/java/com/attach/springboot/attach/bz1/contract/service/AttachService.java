package com.attach.springboot.attach.bz1.contract.service;

import com.attach.springboot.attach.bz1.contract.model.Base64FileInfo;
import com.attach.springboot.attach.bz1.contract.model.FileInfo;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachService {

    /**
     * 上传多个文件
     *
     * @param multipartFiles    文件数据
     * @param metadata 文件元数据
     * @return 文件DTO对象
     */
    List<FileInfo> batchUpload(MultipartFile[] multipartFiles, String metadata);

    /**
     * 上传单个文件
     *
     * @param base64FileInfo Base64文件DTO对象
     * @return 文件DTO对象
     */
    FileInfo uploadFileString(Base64FileInfo base64FileInfo);

    /**
     * 处理下载或者预览请求
     *
     * @param fileId     文件ID
     * @param isDownload 是否是下载, true: 下载, false: 预览
     * @return ResponseEntity
     */
    ResponseEntity<InputStreamResource> handleDownload(String fileId, boolean isDownload);

}