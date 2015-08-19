package com.lite.jop.platform.security;

/**
 * AppSecurityManager
 *
 * @author LaineyC
 */
public interface AppSecurityManager {

    /**
     * 获取应用程序的密钥
     *
     * @param appKey
     * @return
     */
    public String getSecret(String appKey);

    /**
     * 是否是合法的appKey
     *
     * @param appKey
     * @return
     */
    public boolean isValidAppKey(String appKey);

}
