package com.lite.jop.foundation;

import com.lite.jop.foundation.interfaces.ServiceResultHandler;
import com.lite.jop.foundation.util.ReflectionUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ServiceResultHandlerManager
 *
 * @author LaineyC
 */
public class ServiceResultHandlerManager {

    private final Map<Class, ServiceResultHandler> serviceResultHandlerCache = new HashMap<>();
    {
        List<ServiceAnnotationConfig<com.lite.jop.foundation.annotation.ServiceResultHandler>> resultHandlerConfigs = ServiceManager.getServiceConfig().getServiceResultHandlers();
        resultHandlerConfigs.forEach(config -> {
            Class<? extends ServiceResultHandler> resultHandlerClass = config.getAnnotation().value();
            ServiceResultHandler resultHandler = ServiceManager.getServiceContainer().getInstance(resultHandlerClass);
            Class resultClass = ReflectionUtil.getSuperClassGenericsType(resultHandlerClass, 0);
            if(resultClass == null){
                resultClass = ReflectionUtil.getSuperClassGenericsType(resultHandlerClass, ServiceResultHandler.class, 0);
            }
            serviceResultHandlerCache.put(resultClass, resultHandler);
        });
    }

    public ServiceResultHandlerManager(){

    }

    public void handleResult(Object result, ServiceContext serviceContext){
        if(result == null){
            throw new ServiceResultNullException("处理结果为NULL");
        }
        ServiceResultHandler resultHandler = serviceResultHandlerCache.get(result.getClass());
        if(resultHandler == null){
            for(Map.Entry<Class, ServiceResultHandler> entry : serviceResultHandlerCache.entrySet()){
                if(entry.getKey().isInstance(result)){
                    resultHandler = entry.getValue();
                    break;
                }
            }
        }
        if(resultHandler == null){
            throw  new ServiceException("结果处理器为NULL");
        }
        resultHandler.handleResult(result, serviceContext);
    }

}
