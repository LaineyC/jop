package com.lite.jop.platform.config;

import com.lite.jop.foundation.annotation.ServiceMethod;

import java.lang.annotation.*;

/**
 * IgnoreSign
 *
 * @author LaineyC
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ServiceMethod
public @interface IgnoreSign {

}
