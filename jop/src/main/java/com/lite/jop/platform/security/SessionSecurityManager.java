package com.lite.jop.platform.security;

/**
 * SessionManager
 *
 * @author LaineyC
 */
public interface SessionSecurityManager {

    /**
     * 注册一个会话
     *
     * @param session
     */
    public void addSession(String sessionId, Object session);

    /**
     * 从注册表中获取会话
     *
     * @param sessionId
     * @return
     */
    public <S> S getSession(String sessionId);

    /**
     * 移除这个会话
     *
     * @param sessionId
     * @return
     */
    public <S> S removeSession(String sessionId);

}
