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
public @interface ServiceInterceptors {

    public ServiceInterceptor[] value();

}
