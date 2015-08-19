package com.lite.jop.foundation.interfaces;

import com.lite.jop.foundation.ServiceContext;

/**
 * ServiceInterceptor
 *
 * @author LaineyC
 */
public interface ServiceInterceptor {

    public Object intercept(ServiceContext serviceContext);

}
