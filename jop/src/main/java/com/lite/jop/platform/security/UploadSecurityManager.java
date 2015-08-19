package com.lite.jop.platform.security;

/**
 * UploadSecurityManager
 *
 * @author LaineyC
 */
public interface UploadSecurityManager {

    /**
     * 上传文件的类型是否是允许
     * @param fileType
     * @return
     */
    public boolean isAllowFileType(String fileType);

    /**
     * 是否超过了上传大文件小的限制
     * @param fileSize
     * @return
     */
    public boolean isExceedFileSize(int fileSize);

    /**
     * 是否超过了上传大小的限制
     * @param fileSize
     * @return
     */
    public boolean isExceedMaxSize(int fileSize);

}
