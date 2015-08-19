package com.lite.jop.platform.interceptor;

import com.lite.jop.foundation.ServiceContext;
import com.lite.jop.foundation.ServiceManager;
import com.lite.jop.foundation.ServiceProxy;
import com.lite.jop.foundation.ServiceResultHandlerManager;
import com.lite.jop.foundation.interfaces.ServiceInterceptor;
import com.lite.jop.platform.ApiException;
import com.lite.jop.platform.model.*;
import com.lite.jop.platform.model.Error;

/**
 * ResultHandleInterceptor
 *
 * @author LaineyC
 */
public class ResultHandleInterceptor implements ServiceInterceptor {

    private ServiceResultHandlerManager resultHandlerManager = ServiceManager.getServiceContainer().getInstance(ServiceResultHandlerManager.class);

    @Override
    public Object intercept(ServiceContext serviceContext) {
        System.out.println("结果处理拦截器---开始");
        Object result;
        try{
            ServiceProxy serviceProxy = serviceContext.getServiceProxy();
            result = serviceProxy.progress();
        }
        catch (ApiException e){
            JopResponse jopResponse = new JopResponse();
            jopResponse.setErrors(e.getErrors());
            result = jopResponse;
        }
        catch (Exception e){
            JopResponse<Object> jopResponse = new JopResponse<>();
            jopResponse.getErrors().add(new Error("未知异常"));
            result = jopResponse;
        }
        resultHandlerManager.handleResult(result, serviceContext);
        System.out.println("结果处理拦截器---结束，返回值：[" + result + "]");
        return result;
    }

}
