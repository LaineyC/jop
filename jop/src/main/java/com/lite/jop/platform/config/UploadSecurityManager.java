package com.lite.jop.platform.config;

import java.lang.annotation.*;

/**
 * UploadSecurityManager
 *
 * @author LaineyC
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UploadSecurityManager {

    public Class<? extends com.lite.jop.platform.security.UploadSecurityManager> value();

}
