package com.lite.jop.foundation.interfaces;

import com.lite.jop.foundation.ServiceContext;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * ServiceMapper 服务映射器
 * 保证两个个接口方法返回的对象equal
 * @author LaineyC
 */
public interface ServiceMapper {

    /**
     * 每次服务请求都返回一个映射key 与索引的返回值相同 既可获得方法的引用 通过反射调用方法
     * @param serviceContext
     * @return
     */
    public Object mapping(ServiceContext serviceContext);

    /**
     * 为服务的方法创建索引 通过方法上的注解信息来创建
     * @param serviceMethod
     * @return
     */
    public Object index(Method serviceMethod);

}
