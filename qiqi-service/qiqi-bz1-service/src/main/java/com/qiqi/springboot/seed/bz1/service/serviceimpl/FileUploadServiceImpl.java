package com.qiqi.springboot.seed.bz1.service.serviceimpl;

import com.qiqi.springboot.seed.bz1.contract.model.FileMeta;
import com.qiqi.springboot.seed.bz1.contract.service.FileUploadService;
import com.qiqi.springboot.seed.bz1.service.repository.DepartmentRepository;
import com.qiqi.springboot.seed.bz1.service.repository.UserRepository;
import com.qiqi.springboot.seed.bz1.service.repository.goods.GoodsRepository;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xuguoyuan
 * @description 文件上传
 * @date 2020-03-19 16:21
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${app.file-path}")
    private String filePath;

    @Autowired
    UserRepository userRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    GoodsRepository goodsRepository;

    /**
     * 上传文件
     * @param request
     * @param response
     * @param type
     * @return
     */
    @Override
    public FileMeta upload(MultipartHttpServletRequest request, HttpServletResponse response, String type) {
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
            // 读取图片报错
            // throw new BusinessException(ResultStatus.PARAM_TYPE_ERROR);
        } finally {
            return null;
        }
    }

    /**
     * 清除无用图片，慎用
     *
     * @return
     */
    @Override
    public boolean clearFiles() {
        // 1 用户头像
        List<String> heads = userRepository.getImages();
        // 2 部门头像
        List<String> depts = departmentRepository.getImages();
        // 3 商品图片链接
        List<String> urlImages = new ArrayList<>();
        List<Object[]> goods = goodsRepository.getImages();
        for (Object[] good : goods) {
            if (null != good[0]) {
                urlImages.addAll(getUrlImgs(String.valueOf(good[0])));
            }
            if (null != good[1]) {
                urlImages.addAll(getUrlImgs(String.valueOf(good[1])));
            }
            if (null != good[2]) {
                urlImages.addAll(getUrlImgs(String.valueOf(good[2])));
            }
        }
        // 4 删除文件夹文件
        File file = new File(filePath);
        File[] tempList = file.listFiles();
        for (File item : tempList) {
            if (item.isFile()) {
                if (!heads.contains(item.getName()) && !depts.contains(item.getName()) && !urlImages.contains(item.getName())) {
                    item.delete();
                }
            }
        }
        return true;
    }

    /**
     * 截取特征 "http://xxx//upload/preview/xxx.xx"
     * @param urls
     * @return
     */
    public List<String>  getUrlImgs(String urls) {
        String regex = "(ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\&%\\+\\$#_=]*)?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(urls);

        List<String> urlImgs = new ArrayList<>();
        while (matcher.find()) {
            String url = matcher.group(0);
            int index = url.indexOf("/upload/preview/");
            urlImgs.add(url.substring(index + 16, url.length()));
        }
        return urlImgs;
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        String a = "v1/upload/preview/1614257633139.png\"";
        int start = a.indexOf("/upload/preview/");
        System.out.println(start);
        System.out.println(a.substring(start + 16, a.length() - 1));
    }
}
