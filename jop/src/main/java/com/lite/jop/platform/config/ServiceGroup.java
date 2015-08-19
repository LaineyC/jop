package com.lite.jop.platform.config;

import com.lite.jop.foundation.annotation.ServiceInterceptor;
import com.lite.jop.foundation.annotation.ServiceInterceptors;
import com.lite.jop.platform.interceptor.*;

import java.lang.annotation.*;

/**
 * ServiceGroup
 *
 * @author LaineyC
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ServiceInterceptors({
    @ServiceInterceptor(ResultHandleInterceptor.class),
    @ServiceInterceptor(UploadSecurityInterceptor.class),
    @ServiceInterceptor(SystemParameterInterceptor.class),
    @ServiceInterceptor(HttpSecurityInterceptor.class),
    @ServiceInterceptor(SessionSecurityInterceptor.class),
    @ServiceInterceptor(AppSecurityInterceptor.class),
    @ServiceInterceptor(ServiceParameterInterceptor.class)
})
public @interface ServiceGroup {

}
