package com.lite.jop.platform.interceptor;

import com.lite.jop.foundation.ServiceAnnotationConfig;
import com.lite.jop.foundation.ServiceContext;
import com.lite.jop.foundation.ServiceManager;
import com.lite.jop.foundation.ServiceProxy;
import com.lite.jop.foundation.interfaces.ServiceInterceptor;
import com.lite.jop.platform.*;
import com.lite.jop.platform.model.*;
import com.lite.jop.platform.model.Error;
import com.lite.jop.platform.protocol.json.JSON;
import com.lite.jop.platform.security.UploadSecurityManager;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * UploadSecurityInterceptor
 *
 * @author LaineyC
 */
public class UploadSecurityInterceptor implements ServiceInterceptor {

    private UploadSecurityManager uploadSecurityManager;

    public UploadSecurityInterceptor(){
        JopServiceConfig serviceConfig = ServiceManager.getServiceConfig();
        ServiceAnnotationConfig<com.lite.jop.platform.config.UploadSecurityManager> uploadSecurityManagerAnnotationConfig = serviceConfig.getUploadSecurityManager();
        Class<? extends UploadSecurityManager> uploadSecurityManagerClass = uploadSecurityManagerAnnotationConfig.getAnnotation().value();
        uploadSecurityManager = ServiceManager.getServiceContainer().getInstance(uploadSecurityManagerClass);
    }

    @Override
    public Object intercept(ServiceContext serviceContext) {
        System.out.println("上传安全拦截器---开始");
        JopServiceContext jopServiceContext = (JopServiceContext)serviceContext;
        ServiceProxy serviceProxy = jopServiceContext.getServiceProxy();
        HttpServletRequest httpServletRequest = jopServiceContext.getHttpServletRequest();
        Object result;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(httpServletRequest.getInputStream(), SystemConstant.CHARSET_UTF8));
            String line;
            StringBuilder sb = new StringBuilder();
            int size = 0;
            while((line = br.readLine()) != null){
                sb.append(line);
                size += line.length();
                if (!uploadSecurityManager.isExceedMaxSize(size)){
                    throw new ApiException(new Error("数据上传超过限制"));
                }
            }
            String requestBody = sb.toString();
            Method serviceMethod = serviceProxy.getServiceMethod();
            Class<?>[] parameterTypes = serviceMethod.getParameterTypes();
            Class<? extends JopRequest> requestClass = (Class<? extends JopRequest>) parameterTypes[0];
            JopRequest<?> request = JSON.jsonStringToJavaBean(requestBody, requestClass);
            if(request instanceof  UploadRequest){
                UploadRequest uploadRequest = (UploadRequest)request;
                FileItem[] fileItems = uploadRequest.uploadFiles();
                if(fileItems != null){
                    for(FileItem fileItem : fileItems){
                        if(!uploadSecurityManager.isAllowFileType(fileItem.getMimeType())){
                            throw new ApiException(new Error("文件[" + fileItem.getFileName() + "]上传类型不支持"));
                        }
                        if(!uploadSecurityManager.isExceedFileSize(fileItem.getContent().length)){
                            throw new ApiException(new Error("文件[" + fileItem.getFileName() + "]上传大小超过限制"));
                        }
                    }
                }
            }
            jopServiceContext.setServiceParameter(request);
            result = serviceProxy.progress();
        }
        catch (Exception e){
            if(e instanceof ApiException){
                throw (ApiException)e;
            }
            throw new JopException(e);
        }
        System.out.println("上传安全拦截器---结束");
        return result;
    }

}