package com.lite.jop.platform.interceptor;

import com.lite.jop.foundation.ServiceAnnotationConfig;
import com.lite.jop.foundation.ServiceContext;
import com.lite.jop.foundation.ServiceManager;
import com.lite.jop.foundation.ServiceProxy;
import com.lite.jop.foundation.interfaces.ServiceInterceptor;
import com.lite.jop.platform.ApiException;
import com.lite.jop.platform.JopServiceConfig;
import com.lite.jop.platform.JopServiceContext;
import com.lite.jop.platform.config.Service;
import com.lite.jop.platform.config.UploadSecurityManager;
import com.lite.jop.platform.model.*;
import com.lite.jop.platform.model.Error;
import com.lite.jop.platform.security.SessionSecurityManager;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * SessionSecurityInterceptor
 *
 * @author LaineyC
 */
public class SessionSecurityInterceptor implements ServiceInterceptor {

    private SessionSecurityManager sessionSecurityManager;

    public SessionSecurityInterceptor(){
        JopServiceConfig serviceConfig = ServiceManager.getServiceConfig();
        ServiceAnnotationConfig<com.lite.jop.platform.config.SessionSecurityManager> sessionSecurityManagerAnnotationConfig = serviceConfig.getSessionSecurityManager();
        Class<? extends SessionSecurityManager> sessionSecurityManagerClass = sessionSecurityManagerAnnotationConfig.getAnnotation().value();
        sessionSecurityManager = ServiceManager.getServiceContainer().getInstance(sessionSecurityManagerClass);
    }

    @Override
    public Object intercept(ServiceContext serviceContext) {
        System.out.println("会话安全拦截器---开始");

        JopServiceContext jopServiceContext = (JopServiceContext)serviceContext;
        ServiceProxy serviceProxy = jopServiceContext.getServiceProxy();
        Object result;
        Service service = serviceProxy.getServiceMethod().getAnnotation(Service.class);
        if(service.needSession()){
            String sessionId = jopServiceContext.getSystemParameter().getSession();
            if(sessionId == null){
                throw new ApiException(new Error("缺少sessionId"));
            }
            if(sessionSecurityManager.getSession(sessionId) == null){
                throw new ApiException(new Error("无效sessionId"));
            }
        }
        result = serviceProxy.progress();

        System.out.println("会话安全拦截器---结束");
        return result;
    }

}