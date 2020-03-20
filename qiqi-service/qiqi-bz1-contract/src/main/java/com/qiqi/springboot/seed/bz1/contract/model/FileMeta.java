package com.qiqi.springboot.seed.bz1.contract.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author xuguoyuan
 * @description
 * @date 2020-03-19 16:17
 */
@JsonIgnoreProperties({"bytes"})
public class FileMeta {
    private boolean statu;
    private String fileName;
    private String filesize;
    private String fileType;
    private byte[] bytes;
    private String path;

    public boolean isStatu() {
        return statu;
    }

    public void setStatu(boolean statu) {
        this.statu = statu;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
