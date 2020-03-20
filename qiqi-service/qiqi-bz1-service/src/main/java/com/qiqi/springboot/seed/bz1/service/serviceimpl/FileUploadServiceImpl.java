package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.model.FileMeta;
import com.qiqi.springboot.seed.bz1.contract.service.FileUploadService;
import com.qiqi.springboot.seed.bz1.service.repository.UserRepository;
import com.qiqi.springboot.seed.common.exception.BusinessException;
import com.qiqi.springboot.seed.common.exception.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Iterator;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-19 16:21
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${app.file-path}")
    private String filePath;

    @Autowired
    UserRepository userRepository;
    /**
     * 上传用户头像
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public FileMeta uploadHeadImg(MultipartHttpServletRequest request, HttpServletResponse response) {
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;

        while(((Iterator) itr).hasNext()){
            mpf = request.getFile(itr.next());
        }
        FileMeta fileMeta = new FileMeta();
        if(mpf == null) {
            fileMeta.setStatu(false);
            return fileMeta;
        }
        String oldName = mpf.getOriginalFilename();
        String[] oldNames = oldName.split("\\.");
        if ( oldNames.length < 2) {
            fileMeta.setStatu(false);
            fileMeta.setFileName("");
            return null;
        }
        String newName = Calendar.getInstance().getTimeInMillis()+"." + oldNames[1];
        String fullPath = filePath + newName;
        fileMeta.setStatu(true);
        fileMeta.setFileName(newName);
        fileMeta.setFilesize(mpf.getSize()/1024 + "kb");
        fileMeta.setFileType(mpf.getContentType());
        fileMeta.setPath(fullPath);
        FileOutputStream outputStream = null;
        try {
            fileMeta.setBytes(mpf.getBytes());
            outputStream = new FileOutputStream(fullPath);
            FileCopyUtils.copy(mpf.getBytes(),outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileMeta;
    }

    /**
     * 处理下载或者预览请求
     *
     * @param fileId
     * @param isDownload
     * @return
     */
    @Override
    public ResponseEntity<InputStreamResource> handleDownload(String fileId, boolean isDownload) {
        String fullPath = filePath + fileId;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Pragma", "no-cache");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Expires", "0");
        try {
            if (isDownload) {
                String filename = "";
                if (StringUtils.isEmpty(filename)) {
                    filename = "file";
                }
                headers.add("Content-Disposition", "attachment; filename="
                        + URLEncoder.encode(filename, "UTF-8"));
            }
            FileInputStream inputStream = new FileInputStream(fullPath);
            return ResponseEntity.ok().headers(headers)
                    .body(new InputStreamResource(inputStream));

        } catch (IOException e) {
            throw new BusinessException(ResultStatus.PARAM_TYPE_ERROR);
        }
    }
}
