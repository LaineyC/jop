package com.lite.jop.foundation.annotation;

import java.lang.annotation.*;

/**
 * ServiceListener
 *
 * @author LaineyC
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceListeners {

    public ServiceListener[] value();

}
