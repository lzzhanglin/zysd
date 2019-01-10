package com.zysd.test.entity;


/**
 * @author lzzhanglin
 * @date 2019/1/10 15:00
 */
public class UploadFile {

    private Long fileId;

    private String fileName;

    private String uploadTime;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
}
