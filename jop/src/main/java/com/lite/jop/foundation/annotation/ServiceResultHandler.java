package com.lite.jop.foundation.annotation;

import java.lang.annotation.*;

/**
 * ServiceResultHandler
 *
 * @author LaineyC
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(ServiceResultHandlers.class)
public @interface ServiceResultHandler {

    public Class<? extends com.lite.jop.foundation.interfaces.ServiceResultHandler> value();

}
