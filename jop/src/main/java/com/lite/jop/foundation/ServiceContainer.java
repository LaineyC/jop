package com.lite.jop.foundation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ServiceContainer
 *
 * @author LaineyC
 */
public class ServiceContainer implements com.lite.jop.foundation.interfaces.ServiceContainer {

    private Map<Class<?>, Object> cache = new ConcurrentHashMap<>();

    private Map<Class<?>, Class<?>> registry = new ConcurrentHashMap<>();

    @Override
    public <T> T getInstance(Class<T> clazz) {
        Class<?> impl = registry.get(clazz);
        T t;
        if(impl == null){
            try {
                t = clazz.newInstance();
            }
            catch (Exception e){
                throw new ServiceException("实例化组件失败：" + clazz.getName(), e);
            }
            cache.put(clazz, t);
            registry.put(clazz, clazz);
            return t;
        }
        t = (T) cache.get(impl);
        if (t == null) {
            try {
                t = (T)impl.newInstance();
            }
            catch (Exception e){
                throw new ServiceException("实例化组件失败：" + impl.getName(), e);
            }
        }
        cache.put(clazz, t);
        return t;
    }

}