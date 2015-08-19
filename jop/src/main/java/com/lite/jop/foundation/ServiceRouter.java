package com.lite.jop.foundation;

import com.lite.jop.foundation.annotation.ServiceInterceptor;
import com.lite.jop.foundation.annotation.ServiceListener;
import com.lite.jop.foundation.annotation.ServiceMethod;
import com.lite.jop.foundation.annotation.ServiceProvider;
import com.lite.jop.foundation.interfaces.ServiceContainer;
import com.lite.jop.foundation.interfaces.ServiceMapper;
import com.lite.jop.foundation.util.AnnotationUtil;
import java.lang.reflect.Method;
import java.util.*;

/**
 * ServiceRouter
 *
 * @author LaineyC
 */
public class ServiceRouter {

    private ServiceConfig serviceConfig = ServiceManager.getServiceConfig();

    private ServiceContainer serviceContainer = ServiceManager.getServiceContainer();

    private ServiceMapper serviceMapper;

    private Map<Method, ServiceProxy<Object>> serviceRegistry = new HashMap<>();

    private Map<Object, Method> indexRegistry = new HashMap<>();

    public ServiceRouter(){
        com.lite.jop.foundation.annotation.ServiceMapper serviceMapperClass = serviceConfig.getServiceMapper().getAnnotation();
        serviceMapper = serviceContainer.getInstance(serviceMapperClass.value());
        createServiceRegistry();
        serviceRegistry.forEach((method, proxy) -> {
            Object serviceKey = serviceMapper.index(method);
            if(indexRegistry.containsKey(serviceKey)){
                Method methodOld = indexRegistry.get(serviceKey);
                throw new ServiceException("索引[" + serviceKey + "]匹配到多个Method[" + method.getName() + ", " + methodOld.getName() + "]！");
            }
            indexRegistry.put(serviceKey, method);
        });
    }

    public void route(ServiceContext serviceContext){
        ServiceProxy<Object> serviceProxy = null;
        try{
            createServiceContext(serviceContext);
            Object methodKey = serviceMapper.mapping(serviceContext);
            Method serviceMethod = indexRegistry.get(methodKey);
            if(serviceMethod == null){
                throw new ServiceInvalidMappingException("无效的服务映射[" + methodKey + "]！");
            }
            serviceProxy = serviceRegistry.get(serviceMethod);
            ServiceContext.getContext().setServiceProxy(serviceProxy);
            serviceProxy.progress();
        }
        catch (Exception e){
            if(e instanceof ServiceInvalidMappingException){
                throw e;
            }
            throw new ServiceException("路由服务异常！", e);
        }
        finally {
            if(serviceProxy != null){
                serviceProxy.shutdown();
            }
            cleanupServiceContext();
        }
    }

    private void createServiceContext(ServiceContext serviceContext){
        ServiceContext.setContext(serviceContext);
    }

    private void cleanupServiceContext(){
        ServiceContext.setContext(null);
    }

    private void createServiceRegistry(){
        serviceConfig.getScanClasses().forEach(serviceProviderClass -> {
            if(AnnotationUtil.hasAnnotation(serviceProviderClass, ServiceProvider.class)){
                Object serviceProvider = serviceContainer.getInstance(serviceProviderClass);
                List<ServiceInterceptor> serviceProviderInterceptors = AnnotationUtil.findAnnotation(serviceProviderClass, ServiceInterceptor.class);
                List<ServiceListener> serviceProviderListeners = AnnotationUtil.findAnnotation(serviceProviderClass, ServiceListener.class);
                Method[] methods = serviceProviderClass.getMethods();
                for(Method method : methods){
                    if(AnnotationUtil.hasAnnotation(method, ServiceMethod.class)){
                        List<ServiceInterceptor> serviceMethodInterceptors = AnnotationUtil.findAnnotation(method, ServiceInterceptor.class);
                        List<ServiceListener> serviceMethodListeners = AnnotationUtil.findAnnotation(method, ServiceListener.class);
                        serviceMethodInterceptors = serviceMethodInterceptors.isEmpty() ? serviceProviderInterceptors : serviceProviderInterceptors;
                        serviceMethodListeners = serviceMethodListeners.isEmpty() ? serviceProviderListeners : serviceProviderListeners;
                        ServiceProxy<Object> serviceProxy = new ServiceProxy<>(serviceProvider, method);
                        List<com.lite.jop.foundation.interfaces.ServiceInterceptor> serviceInterceptors = serviceProxy.getServiceInterceptors();
                        List<com.lite.jop.foundation.interfaces.ServiceListener> serviceListeners = serviceProxy.getServiceListeners();
                        serviceMethodInterceptors.forEach(si -> serviceInterceptors.add(serviceContainer.getInstance(si.value())));
                        serviceMethodListeners.forEach(sl -> serviceListeners.add(serviceContainer.getInstance(sl.value())));
                        serviceRegistry.put(method, serviceProxy);
                    }
                }
            }
        });
    }

}
