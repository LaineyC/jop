package com.lite.jop.platform.security;

/**
 * ServiceAccessManager
 *
 * @author LaineyC
 */
public interface ServiceAccessManager {

    /**
     * 服务方法是否向ISV开放
     * @param method
     * @param appKey
     * @return
     */
    public boolean isAppGranted(String appKey, String method, String version);

    /**
     *  服务方法是否向当前用户开放
     * @param session
     * @return
     */
    public boolean isUserGranted(Object session,String method,String version);

}
