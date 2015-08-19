package com.lite.jop.demo.security;

import com.lite.jop.platform.security.UploadSecurityManager;

/**
 * SimpleUploadSecurityManager
 *
 * @author LaineyC
 */
public class SimpleUploadSecurityManager implements UploadSecurityManager {

    public boolean isAllowFileType(String fileType){
        return true;
    }

    public boolean isExceedFileSize(int fileSize) { return true; }

    public boolean isExceedMaxSize(int fileSize){
        return true;
    }

}
