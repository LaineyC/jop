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
@Repeatable(ServiceListeners.class)
public @interface ServiceListener {

    public Class<? extends com.lite.jop.foundation.interfaces.ServiceListener> value();

}
