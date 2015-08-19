package com.lite.jop.platform.config;

import com.lite.jop.foundation.annotation.ServiceProvider;

import java.lang.annotation.*;

/**
 * Provider
 *
 * @author LaineyC
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ServiceProvider
@ServiceGroup
public @interface Provider {

}
