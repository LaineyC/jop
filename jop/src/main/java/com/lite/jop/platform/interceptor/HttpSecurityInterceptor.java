package com.lite.jop.platform.interceptor;

import com.lite.jop.foundation.ServiceContext;
import com.lite.jop.foundation.interfaces.ServiceInterceptor;
import com.lite.jop.platform.ApiException;
import com.lite.jop.platform.JopServiceContext;
import com.lite.jop.platform.config.HttpMethod;
import com.lite.jop.platform.config.Service;
import com.lite.jop.platform.model.*;
import com.lite.jop.platform.model.Error;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpSecurityInterceptor
 *
 * @author LaineyC
 */
public class HttpSecurityInterceptor implements ServiceInterceptor {

    @Override
    public Object intercept(ServiceContext serviceContext) {
        System.out.println("HTTP安全拦截器---开始");
        JopServiceContext jopServiceContext = (JopServiceContext)serviceContext;
        HttpServletRequest request = jopServiceContext.getHttpServletRequest();
        Service service = serviceContext.getServiceProxy().getServiceMethod().getAnnotation(Service.class);
        HttpMethod[] httpMethods =  service.httpMethod();
        if(httpMethods.length > 0){
            boolean isValid = false;
            for(HttpMethod httpMethod : httpMethods){
                if(httpMethod.toString().equals(request.getMethod().toUpperCase())){
                    isValid = true;
                    break;
                }
            }
            if(!isValid){
                throw new ApiException(new Error("非法的HTTP请求方法"));
            }
        }
        Object result = serviceContext.getServiceProxy().progress();
        System.out.println("HTTP安全拦截器---结束");
        return result;
    }

}