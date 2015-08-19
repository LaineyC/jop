package com.lite.jop.platform.interceptor;

import com.lite.jop.foundation.ServiceContext;
import com.lite.jop.foundation.ServiceProxy;
import com.lite.jop.foundation.interfaces.ServiceInterceptor;
import com.lite.jop.platform.ApiException;
import com.lite.jop.platform.JopException;
import com.lite.jop.platform.JopServiceContext;
import com.lite.jop.platform.model.*;
import com.lite.jop.platform.model.Error;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ServiceParameterInterceptor
 *
 * @author LaineyC
 */
public class ServiceParameterInterceptor implements ServiceInterceptor {

    @Override
    public Object intercept(ServiceContext serviceContext) {
        System.out.println("服务参数拦截器---开始");
        Object result;
        JopServiceContext jopServiceContext = (JopServiceContext)serviceContext;
        ServiceProxy serviceProxy = jopServiceContext.getServiceProxy();
        Method serviceMethod = serviceProxy.getServiceMethod();
        try {
            System.out.println("调用服务：" + jopServiceContext.getSystemParameter().getMethod());
            result = serviceMethod.invoke(serviceProxy.getServiceProvider(), jopServiceContext.getServiceParameter());
        }
        catch (Exception e){
            if(e instanceof InvocationTargetException){
                if(e.getCause() instanceof ApiException){
                    throw (ApiException)e.getCause();
                }
            }
            throw new JopException(e);
        }
        System.out.println("服务参数拦截器---结束");
        return result;
    }

}
