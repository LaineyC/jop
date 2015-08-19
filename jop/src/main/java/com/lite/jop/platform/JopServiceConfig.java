package com.lite.jop.platform;

import com.lite.jop.foundation.ServiceAnnotationConfig;
import com.lite.jop.foundation.ServiceConfig;
import com.lite.jop.platform.config.AppSecurityManager;
import com.lite.jop.platform.config.SessionSecurityManager;
import com.lite.jop.platform.config.ThreadPoolExecutor;
import com.lite.jop.platform.config.UploadSecurityManager;

/**
 * JopServiceConfig
 *
 * @author LaineyC
 */
public class JopServiceConfig extends ServiceConfig {

    public JopServiceConfig(String scanPackage) {
      super(scanPackage);
    }

    public ServiceAnnotationConfig<ThreadPoolExecutor> getThreadPoolExecutor(){
        ServiceAnnotationConfig<ThreadPoolExecutor> serviceMapperConfig = getConfig(ThreadPoolExecutor.class);
        if(serviceMapperConfig == null){
            throw new JopException("线程池未配置！");
        }
        return serviceMapperConfig;
    }

    public ServiceAnnotationConfig<AppSecurityManager> getAppSecurityManager(){
        ServiceAnnotationConfig<AppSecurityManager> serviceMapperConfig = getConfig(AppSecurityManager.class);
        if(serviceMapperConfig == null){
            throw new JopException("应用安全管理器未配置！");
        }
        return serviceMapperConfig;
    }

    public ServiceAnnotationConfig<SessionSecurityManager> getSessionSecurityManager(){
        ServiceAnnotationConfig<SessionSecurityManager> serviceMapperConfig = getConfig(SessionSecurityManager.class);
        if(serviceMapperConfig == null){
            throw new JopException("会话安全管理器未配置！");
        }
        return serviceMapperConfig;
    }

    public ServiceAnnotationConfig<UploadSecurityManager> getUploadSecurityManager(){
        ServiceAnnotationConfig<UploadSecurityManager> serviceMapperConfig = getConfig(UploadSecurityManager.class);
        if(serviceMapperConfig == null){
            throw new JopException("上传安全管理器未配置！");
        }
        return serviceMapperConfig;
    }

}
