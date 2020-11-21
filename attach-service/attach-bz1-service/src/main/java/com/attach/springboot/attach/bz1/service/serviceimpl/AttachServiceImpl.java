package com.attach.springboot.attach.bz1.service.serviceimpl;

import com.attach.springboot.attach.bz1.contract.model.Base64FileInfo;
import com.attach.springboot.attach.bz1.contract.model.FileInfo;
import com.attach.springboot.attach.bz1.contract.service.AttachService;
import com.attach.springboot.attach.bz1.service.datamappers.FileMapper;
import com.attach.springboot.attach.bz1.service.entity.FileEntity;
import com.attach.springboot.attach.bz1.service.entity.MetadataEntity;
import com.attach.springboot.attach.bz1.service.repository.SpecialFileRepository;
import com.attach.springboot.attach.common.attach.HttpError;
import com.attach.springboot.attach.common.exception.BusinessException;
import com.attach.springboot.attach.common.exception.ResultStatus;
import com.attach.springboot.attach.common.exception.SystemException;
import com.attach.springboot.attach.common.util.CheckUtils;
import com.attach.springboot.attach.common.util.JsonUtil;
import com.attach.springboot.attach.common.util.MimeTypeUtil;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import io.netty.util.internal.StringUtil;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class AttachServiceImpl implements AttachService {

    private static final Logger logger = LoggerFactory.getLogger(AttachServiceImpl.class);

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFSBucket gridFSBucket;
    @Autowired
    private SpecialFileRepository specialFileRepository;

    /**
     * 上传多个文件
     *
     * @param multipartFiles    文件数据
     * @param metadata 文件元数据
     * @return 文件DTO对象
     */
    @Override
    public List<FileInfo> batchUpload(MultipartFile[] multipartFiles, String metadata) {
        MetadataEntity metadataEntity = checkAndReturnMetadataEntity(metadata);

        Date date = new Date();
        List<FileEntity> fileEntities = Arrays.stream(multipartFiles).map(item -> {
            String originalFilename = item.getOriginalFilename();
            String contentType = item.getContentType();

            try (InputStream inputStream = item.getInputStream()) {
                FileEntity fileEntity = storeFileAndReturnFileEntity(inputStream, originalFilename, contentType, metadataEntity);
                fileEntity.setExtension(MimeTypeUtil.getExtensionByContentType(contentType));
                fileEntity.setSize(item.getSize());
                fileEntity.setUploadDate(date);
                return fileEntity;
            } catch (IOException e) {
                logger.error("batchUpload multipart file error: {}", e.getMessage(), e);
                throw new SystemException(ResultStatus.SYSTEM_INNER_ERROR);
            }

        }).collect(Collectors.toList());

        List<FileEntity> fileEntitiesCopy = specialFileRepository.saveAll(fileEntities);
        return fileMapper.entitiesToModels(fileEntitiesCopy);
    }

    /**
     * 上传单个文件
     *
     * @param base64FileInfo Base64文件DTO对象
     * @return 文件DTO对象
     */
    @Override
    public FileInfo uploadFileString(Base64FileInfo base64FileInfo) {
        MetadataEntity metadataEntity = checkAndReturnMetadataEntity(base64FileInfo.getMetadata());

        String fileStr = base64FileInfo.getBase64String();
        CheckUtils.checkNotEmpty(fileStr);
        // 如果文件字符串不包含base64前缀, 则抛出参数不完整异常
        if (!fileStr.contains(":") || !fileStr.contains(";") || !fileStr.contains(",")) {
            throw new BusinessException(ResultStatus.PARAM_IS_INVALID);
        }

        String contentType = fileStr.substring(fileStr.indexOf(':') + 1, fileStr.indexOf(';'));
        if (StringUtil.isNullOrEmpty(contentType)) {
            contentType = "application/octet-stream";
        }
        String extensionName = MimeTypeUtil.getExtensionByContentType(contentType);
        String fileName = UUID.randomUUID().toString() + extensionName;

        try (InputStream inputStream = getInputStream(fileStr)) {
            FileEntity fileEntity = storeFileAndReturnFileEntity(inputStream, fileName, contentType, metadataEntity);
            fileEntity.setExtension(extensionName);
            fileEntity.setSize(fileStr.length());
            fileEntity.setUploadDate(new Date());

            FileEntity fileEntityCopy = specialFileRepository.save(fileEntity);
            return fileMapper.entityToModel(fileEntityCopy);
        } catch (IOException e) {
            logger.error("uploadFileString base64 file error: {}", e.getMessage(), e);
            throw new SystemException(ResultStatus.SYSTEM_INNER_ERROR);
        }
    }

    /**
     * 处理下载或者预览请求
     *
     * @param fileId     文件ID
     * @param isDownload 是否是下载, true: 下载, false: 预览
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<InputStreamResource> handleDownload(String fileId, boolean isDownload) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Pragma", "no-cache");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Expires", "0");

        GridFSFile gridFSFile = gridFsTemplate.findOne(query(where("_id").is(fileId)));
        if (gridFSFile.getId() == null) {
            if (isDownload) {
                return handleDownloadError(headers, ResultStatus.DATA_NOT_EXIST);
            }
            throw new BusinessException(ResultStatus.DATA_NOT_EXIST);
        }

        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);

        try {
            if (isDownload) {
                String filename = gridFsResource.getFilename();
                if (StringUtil.isNullOrEmpty(filename)) {
                    filename = "file";
                }
                headers.add("Content-Disposition", "attachment; filename="
                        + URLEncoder.encode(filename, "UTF-8"));
            }

            return ResponseEntity.ok().headers(headers)
                    .contentLength(gridFsResource.contentLength())
                    .contentType(MediaType.parseMediaType(gridFsResource.getContentType()))
                    .body(new InputStreamResource(gridFsResource.getInputStream()));

        } catch (IOException e) {
            logger.error("handleDownload error: {}", e.getMessage(), e);
            if (isDownload) {
                return handleDownloadError(headers, ResultStatus.SYSTEM_INNER_ERROR);
            }
            throw new SystemException(ResultStatus.SYSTEM_INNER_ERROR);
        }
    }

    /**
     * 根据文件ID和所属模块删除文件
     *
     * @param fileId 文件ID
     * @param module 文件所属模块
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteByFileIdAndModule(String fileId, String module) {
        Long deleteCount = specialFileRepository.deleteByFileIdAndModule(fileId, module);
        if (deleteCount == null || deleteCount == 0) {
            return false;
        }
        gridFsTemplate.delete(query(where("_id").is(fileId)));
        return true;
    }

    /**
     * 根据文件模块及文件ID集合批量删除文件
     *
     * @param module  文件所属模块
     * @param fileIds 文件ID集合
     * @return 是否删除成功
     */
    @Override
    public Boolean batchDeleteByModuleAndFileIds(String module, List<String> fileIds) {
        Long deleteCount = specialFileRepository.deleteByModuleAndFileIds(module, fileIds);
        if (deleteCount == null || deleteCount == 0) {
            return false;
        }
        gridFsTemplate.delete(query(where("_id").in(fileIds)));
        return true;
    }

    /**
     * 根据文件ID和所属模块查找文件详情
     *
     * @param fileId 文件ID
     * @param module 所属模块
     * @return 文件DTO对象
     */
    @Override
    public FileInfo findByFileIdAndModule(String fileId, String module) {
        FileEntity fileEntity = specialFileRepository.findByFileIdAndModule(fileId, module);
        return fileMapper.entityToModel(fileEntity);
    }

    /**
     * 根据所属模块查询文件集合
     *
     * @param module 所属模块
     * @return 文件DTO对象
     */
    @Override
    public List<FileInfo> findByModule(String module) {
        List<FileEntity> fileEntitys = specialFileRepository.findByModule(module);
        return fileMapper.entitiesToModels(fileEntitys);
    }

    /**
     * 根据系统ID查询文件集合
     *
     * @param system 所属模块
     * @return 文件DTO对象
     */
    @Override
    public List<FileInfo> findBySystem(String system) {
        return fileMapper.entitiesToModels(specialFileRepository.findBySystem(system));
    }

    /**
     * 根据id更新metadata数据
     *
     * @param fileInfo
     */
    @Override
    public FileInfo updateMetadataById(FileInfo fileInfo) {
        FileEntity fileEntity = fileMapper.modelToEntity(fileInfo);
        return fileMapper.entityToModel(specialFileRepository.updateMetadataById(fileEntity));
    }

    /**
     * 校验文件元数据JSON字符串并转换为文件元数据实体对象
     *
     * @param metadata 文件元数据JSON字符串
     * @return 转换后的元数据实体
     */
    private MetadataEntity checkAndReturnMetadataEntity(String metadata) {
        MetadataEntity metadataEntity = null;
        if (!StringUtil.isNullOrEmpty(metadata)) {
            metadataEntity = JsonUtil.fromJson(metadata, MetadataEntity.class);
            CheckUtils.checkNotNull(metadataEntity);
        }
        return metadataEntity;
    }

    /**
     * base64字符串转化成输入流
     *
     * @param fileStr base64字符串
     * @return InputStream输入流
     */
    private InputStream getInputStream(String fileStr) {
        if (StringUtil.isNullOrEmpty(fileStr)) {
            return null;
        }

        String fileDataStr = fileStr.substring(fileStr.indexOf(',') + 1);
        byte[] bytes = Base64.getDecoder().decode(fileDataStr.getBytes());

        return new ByteArrayInputStream(bytes);
    }

    /**
     * 保存并返回文件实体对象
     *
     * @param inputStream    文件输入流
     * @param fileName       文件名
     * @param contentType    文件类型
     * @param metadataEntity 文件元数据实体对象
     * @return 文件实体对象
     */
    private FileEntity storeFileAndReturnFileEntity(InputStream inputStream, String fileName, String contentType, MetadataEntity metadataEntity) {
        ObjectId objectId = gridFsTemplate.store(inputStream, fileName, contentType, metadataEntity);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileId(objectId.toString());
        fileEntity.setFileName(fileName);
        fileEntity.setContentType(contentType);

        return assembleFileMetadata(metadataEntity, fileEntity);
    }

    /**
     * 组装文件元数据信息
     *
     * @param metadataEntity 文件元数据实体对象
     * @param fileEntity     文件实体对象
     * @return 组装后的文件实体对象
     */
    private FileEntity assembleFileMetadata(MetadataEntity metadataEntity, FileEntity fileEntity) {
        if (metadataEntity != null && fileEntity != null) {
            fileEntity.setSystem(metadataEntity.getSystem());
            fileEntity.setModule(metadataEntity.getModule());
            fileEntity.setBusinessId(metadataEntity.getBusinessId());
            fileEntity.setRemark(metadataEntity.getRemark());
        }
        return fileEntity;
    }

    /**
     * 处理文件下载异常, 默认返回error.txt文件, 内容为异常描述
     *
     * @param headers   基本文件头对象
     * @param errorCode 错误枚举对象
     * @return 含错误描述文件流的ResponseEntity对象
     */
    private ResponseEntity<InputStreamResource> handleDownloadError(HttpHeaders headers, ResultStatus errorCode) {
        headers.add("Content-Disposition", "attachment; filename=error.txt");
        HttpError httpError = new HttpError(errorCode.getCode(), errorCode.getMessage());
        byte[] bytes = JsonUtil.toJson(httpError).getBytes();

        return ResponseEntity.ok().headers(headers)
                .contentLength(bytes.length)
                .contentType(MediaType.TEXT_PLAIN)
                .body(new InputStreamResource(new ByteArrayInputStream(bytes)));
    }
}
