package com.lite.jop.foundation.annotation;

import java.lang.annotation.*;

/**
 * ServiceResultHandlers
 *
 * @author LaineyC
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceResultHandlers {

    public ServiceResultHandler[] value();

}
