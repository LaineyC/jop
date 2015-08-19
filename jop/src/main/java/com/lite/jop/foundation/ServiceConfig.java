package com.lite.jop.foundation;

import com.lite.jop.foundation.annotation.ServiceMapper;
import com.lite.jop.foundation.annotation.ServiceResultHandler;
import com.lite.jop.foundation.util.AnnotationUtil;
import com.lite.jop.foundation.util.ClassUtil;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ServiceConfig
 *
 * @author LaineyC
 */
public class ServiceConfig {

    private String scanPackage;

    private List<Class<?>> scanClasses = new ArrayList<>();

    private Map<Class<? extends Annotation>, List> configCache = new ConcurrentHashMap<>();

    public ServiceConfig(String scanPackage) {
        this.scanPackage = scanPackage;
        this.scanClasses =  ClassUtil.findClass(scanPackage);
    }

    protected <A extends Annotation> List<ServiceAnnotationConfig<A>> getConfigs(Class<A> annotationClass){
        if(configCache.containsKey(annotationClass)){
            return (List<ServiceAnnotationConfig<A>>)configCache.get(annotationClass);
        }
        List<ServiceAnnotationConfig<A>> annotationConfigs = new ArrayList<>();
        scanClasses.forEach(clazz -> {
            if(AnnotationUtil.hasAnnotation(clazz, annotationClass)){
                List<A> annotations = AnnotationUtil.findAnnotation(clazz, annotationClass);
                annotations.forEach(annotation -> annotationConfigs.add(new ServiceAnnotationConfig<>(annotation, clazz)));
            }
        });
        configCache.put(annotationClass, annotationConfigs);
        return annotationConfigs;
    }

    protected <A extends Annotation> ServiceAnnotationConfig<A> getConfig(Class<A> annotationClass){
        List<ServiceAnnotationConfig<A>> annotationConfigs = getConfigs(annotationClass);
        if(annotationConfigs.isEmpty()){
            return null;
        }
        if(annotationConfigs.size() > 1){
            throw new ServiceException("预期匹配到一个，但匹配到多个：" + annotationClass.getName());
        }
        return annotationConfigs.get(0);
    }

    public ServiceAnnotationConfig<ServiceMapper> getServiceMapper(){
        ServiceAnnotationConfig<ServiceMapper> serviceMapperConfig = getConfig(ServiceMapper.class);
        if(serviceMapperConfig == null){
            throw new ServiceException("服务映射器未配置！");
        }
        return serviceMapperConfig;
    }

    public List<ServiceAnnotationConfig<ServiceResultHandler>> getServiceResultHandlers(){
        List<ServiceAnnotationConfig<ServiceResultHandler>> serviceAnnotationConfigs = getConfigs(ServiceResultHandler.class);
        if(serviceAnnotationConfigs.isEmpty()){
            throw new ServiceException("服务结果处理器未配置！");
        }
        return serviceAnnotationConfigs;
    }

    public List<Class<?>> getScanClasses() {
        return scanClasses;
    }

    public String getScanPackage() {
        return scanPackage;
    }

}
