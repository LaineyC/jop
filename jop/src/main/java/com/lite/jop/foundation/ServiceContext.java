package com.lite.jop.foundation;

/**
 * ServiceContext
 *
 * @author LaineyC
 */
public class ServiceContext {

    private static ThreadLocal<ServiceContext> serviceContext = new ThreadLocal<>();

    private ServiceProxy serviceProxy;

    public ServiceContext() {

    }

    public static <T extends ServiceContext> T getContext(){
        return (T)ServiceContext.serviceContext.get();
    }

    public static void setContext(ServiceContext context){
        ServiceContext.serviceContext.set(context);
    }

    public ServiceProxy getServiceProxy(){
        return serviceProxy;
    }

    public void setServiceProxy(ServiceProxy serviceProxy){
            this.serviceProxy  = serviceProxy;
    }

}
