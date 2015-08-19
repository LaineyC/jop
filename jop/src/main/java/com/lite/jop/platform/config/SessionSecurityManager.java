package com.lite.jop.platform.config;

import java.lang.annotation.*;

/**
 * SessionSecurityManager
 *
 * @author LaineyC
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SessionSecurityManager {

    public Class<? extends com.lite.jop.platform.security.SessionSecurityManager> value();

}
