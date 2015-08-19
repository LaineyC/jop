package com.lite.jop.platform.model;

/**
 * UploadRequest
 *
 * @author LaineyC
 */
public abstract class UploadRequest<R extends JopResponse> extends JopRequest<R> {

    public abstract String serviceMethod();

    public abstract Class<R> responseClass();

    public abstract FileItem[] uploadFiles();

}
