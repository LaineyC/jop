package com.lite.jop.demo.security;

import com.lite.jop.platform.security.SessionSecurityManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SessionManager
 *
 * @author LaineyC
 */
public class SimpleSessionSecurityManager implements SessionSecurityManager {

    private Map<String, Object> sessionMap = new ConcurrentHashMap<>();

    public void addSession(String sessionId, Object session){
        sessionMap.put(sessionId, session);
    }

    public <S> S getSession(String sessionId){
        return (S)sessionMap.get(sessionId);
    }


    public <S> S removeSession(String sessionId){
        return (S)sessionMap.remove(sessionId);
    }

}
