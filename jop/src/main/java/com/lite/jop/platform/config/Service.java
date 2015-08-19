package com.lite.jop.platform.config;

import com.lite.jop.foundation.annotation.ServiceMethod;

import java.lang.annotation.*;

/**
 * ApiMethod
 *
 * @author LaineyC
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ServiceMethod
public @interface Service {

    public String method();

    public String version() default "";

    public HttpMethod[] httpMethod() default {};

    public boolean needSession() default true;

    public boolean needSign() default true;

}
