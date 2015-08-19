package com.lite.jop.platform.config;

import java.lang.annotation.*;

/**
 * AppSecurityManager
 *
 * @author LaineyC
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AppSecurityManager {

    public Class<? extends com.lite.jop.platform.security.AppSecurityManager> value();
}
