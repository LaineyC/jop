package com.lite.jop.platform.interceptor;

import com.lite.jop.foundation.ServiceAnnotationConfig;
import com.lite.jop.foundation.ServiceContext;
import com.lite.jop.foundation.ServiceManager;
import com.lite.jop.foundation.interfaces.ServiceInterceptor;
import com.lite.jop.platform.*;
import com.lite.jop.platform.model.*;
import com.lite.jop.platform.model.Error;
import com.lite.jop.platform.security.AppSecurityManager;
import com.lite.jop.platform.util.SignUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * AppSecurityInterceptor
 *
 * @author LaineyC
 */
public class AppSecurityInterceptor implements ServiceInterceptor {

    private AppSecurityManager appSecurityManager;

    public AppSecurityInterceptor(){
        JopServiceConfig serviceConfig = ServiceManager.getServiceConfig();
        ServiceAnnotationConfig<com.lite.jop.platform.config.AppSecurityManager> appSecurityManagerAnnotationConfig = serviceConfig.getAppSecurityManager();
        Class<? extends AppSecurityManager> appSecurityManagerClass = appSecurityManagerAnnotationConfig.getAnnotation().value();
        appSecurityManager = ServiceManager.getServiceContainer().getInstance(appSecurityManagerClass);
    }

    @Override
    public Object intercept(ServiceContext serviceContext) {
        System.out.println("应用安全拦截器---开始");
        JopServiceContext jopServiceContext = (JopServiceContext)serviceContext;
        SystemParameter systemParameter = jopServiceContext.getSystemParameter();
        JopRequest<?> jopRequest = jopServiceContext.getServiceParameter();
        String appKey = systemParameter.getApp();
        if(!appSecurityManager.isValidAppKey(appKey)){
            throw new ApiException(new Error("非法应用键"));
        }
        String secret = appSecurityManager.getSecret(appKey);
        Map<String, String> protocalParams = new HashMap<>();
        protocalParams.put(SystemConstant.METHOD, systemParameter.getMethod());
        if(systemParameter.getVersion() != null){
            protocalParams.put(SystemConstant.VERSION, systemParameter.getVersion());
        }
        protocalParams.put(SystemConstant.APP, systemParameter.getApp());
        if(systemParameter.getSession() != null){
            protocalParams.put(SystemConstant.SESSION, systemParameter.getSession());
        }
        String sign = SignUtil.sign(protocalParams, secret, jopRequest);
        if(!sign.equals(systemParameter.getSign())){
            throw new ApiException(new Error("非法签名"));
        }
        Object result = serviceContext.getServiceProxy().progress();
        System.out.println("应用安全拦截器---结束");
        return result;
    }

}