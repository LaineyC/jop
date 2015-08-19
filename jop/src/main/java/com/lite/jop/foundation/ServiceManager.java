package com.lite.jop.foundation;

import com.lite.jop.foundation.interfaces.ServiceContainer;

/**
 * ServiceManager
 *
 * @author LaineyC
 */
public class ServiceManager {

    private static ServiceConfig serviceConfig;

    private static ServiceContainer serviceContainer = new com.lite.jop.foundation.ServiceContainer();

    private ServiceManager(){

    }

    public static <X extends ServiceConfig> X getServiceConfig() {
        return (X)serviceConfig;
    }

    public static void setServiceConfig(ServiceConfig serviceConfig) {
        ServiceManager.serviceConfig = serviceConfig;
    }

    public static ServiceContainer getServiceContainer() {
        return serviceContainer;
    }

    public static void setServiceContainer(ServiceContainer serviceContainer) {
        ServiceManager.serviceContainer = serviceContainer;
    }

}
