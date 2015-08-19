package com.lite.jop.platform.security;

/**
 * InvokeTimesManager
 *
 * @author LaineyC
 */
public interface InvokeTimesManager {


    /**
     * 用户服务访问次数是否超限
     * @param session
     * @return
     */
    public boolean isUserInvokeLimitExceed(String appKey, Object session);

    /**
     * 某个会话的服务访问次数是否超限
     * @param sessionId
     * @return
     */
    public boolean isSessionInvokeLimitExceed(String appKey, String sessionId);

    /**
     * 某个会话的服务访问频率是否超限
     * @param sessionId
     * @return
     */
    public boolean isSessionInvokeFrequencyExceed(String appKey, String sessionId);

    /**
     * 应用的服务访问次数是否超限
     * @param appKey
     * @return
     */
    public boolean isAppInvokeLimitExceed(String appKey);

    /**
     * 应用对服务的访问频率是否超限
     * @param appKey
     * @return
     */
    public boolean isAppInvokeFrequencyExceed(String appKey);

}
