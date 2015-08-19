package com.lite.jop.foundation;

import com.lite.jop.foundation.interfaces.ServiceInterceptor;
import com.lite.jop.foundation.interfaces.ServiceListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ServiceProxy
 * @param <T> 被代理的服务类型
 * @author LaineyC
 */

public class ServiceProxy<T> {

    private List<ServiceInterceptor> serviceInterceptors = new ArrayList<>();

    private List<ServiceListener> serviceListeners = new ArrayList<>();

    private ThreadLocal<Iterator<ServiceInterceptor>> threadInterceptors = new ThreadLocal<>();

    private T serviceProvider;

    private Method serviceMethod;

    public ServiceProxy(T serviceProvider, Method serviceMethod) {
        this.serviceProvider = serviceProvider;
        this.serviceMethod = serviceMethod;
    }

    public ServiceProxy(List<ServiceInterceptor> serviceInterceptors, List<ServiceListener> serviceListeners, T serviceProvider, Method serviceMethod) {
        this.serviceInterceptors = serviceInterceptors;
        this.serviceListeners = serviceListeners;
        this.serviceProvider = serviceProvider;
        this.serviceMethod = serviceMethod;
    }

    public T getServiceProvider(){
        return serviceProvider;
    }

    public Method getServiceMethod(){
        return serviceMethod;
    }

    public ServiceContext getServiceContext(){
        return ServiceContext.getContext();
    }

    public List<ServiceInterceptor> getServiceInterceptors() {
        return serviceInterceptors;
    }

    public List<ServiceListener> getServiceListeners() {
        return serviceListeners;
    }

    /**
     * 服务进行下去
     * @return
     */
    public Object progress(){
        Object rt = null;
        Iterator<ServiceInterceptor> it = threadInterceptors.get();
        if(it == null){
            it = serviceInterceptors.iterator();
            threadInterceptors.set(it);
        }
        if(it.hasNext()) {
            rt = it.next().intercept(getServiceContext());
        }
        else {
            shutdown();
        }
        return rt;
    }

    /**
     * 强行停止服务
     * @return
     */
    public void shutdown (){
        threadInterceptors.set(null);
    }

}
