package com.lite.jop.foundation.annotation;

import java.lang.annotation.*;

/**
 * ServiceMapper
 *
 * @author LaineyC
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceMapper {

    public Class<? extends com.lite.jop.foundation.interfaces.ServiceMapper> value();

}
