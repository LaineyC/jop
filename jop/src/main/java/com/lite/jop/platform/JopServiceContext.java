package com.lite.jop.platform;

import com.lite.jop.foundation.ServiceAnnotationConfig;
import com.lite.jop.foundation.ServiceContext;
import com.lite.jop.foundation.ServiceManager;
import com.lite.jop.platform.model.JopRequest;
import com.lite.jop.platform.model.SystemParameter;
import com.lite.jop.platform.security.SessionSecurityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JopServiceContext
 *
 * @author LaineyC
 */
public class JopServiceContext extends ServiceContext {

    private static SessionSecurityManager sessionSecurityManager;

    static {
        JopServiceConfig serviceConfig = ServiceManager.getServiceConfig();
        ServiceAnnotationConfig<com.lite.jop.platform.config.SessionSecurityManager> sessionSecurityManagerAnnotationConfig = serviceConfig.getSessionSecurityManager();
        if(sessionSecurityManagerAnnotationConfig != null){
            Class<? extends SessionSecurityManager> sessionSecurityManagerClass = sessionSecurityManagerAnnotationConfig.getAnnotation().value();
            sessionSecurityManager = ServiceManager.getServiceContainer().getInstance(sessionSecurityManagerClass);
        }
    }

    private HttpServletRequest httpServletRequest;

    private HttpServletResponse httpServletResponse;

    private SystemParameter systemParameter;

    private JopRequest serviceParameter;

    public JopServiceContext() {

    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public SystemParameter getSystemParameter() {
        return systemParameter;
    }

    public void setSystemParameter(SystemParameter systemParameter) {
        this.systemParameter = systemParameter;
    }

    public JopRequest getServiceParameter() {
        return serviceParameter;
    }

    public void setServiceParameter(JopRequest serviceParameter) {
        this.serviceParameter = serviceParameter;
    }

    public void addSession(String sessionId, Object session){
        sessionSecurityManager.addSession(sessionId, session);
    }

    public <S> S removeSession(){
        return sessionSecurityManager.removeSession(systemParameter.getSession());
    }

    public <S> S getSession(){
        return sessionSecurityManager.getSession(systemParameter.getSession());
    }

}
