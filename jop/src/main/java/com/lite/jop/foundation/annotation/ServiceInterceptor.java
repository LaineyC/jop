package com.lite.jop.foundation.annotation;

import java.lang.annotation.*;

/**
 * ServiceInterceptor
 *
 * @author LaineyC
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(ServiceInterceptors.class)
public @interface ServiceInterceptor {

    public Class<? extends com.lite.jop.foundation.interfaces.ServiceInterceptor> value();

}
