package com.lite.jop.demo;

import com.lite.jop.demo.security.SimpleAppSecurityManager;
import com.lite.jop.demo.security.SimpleSessionSecurityManager;
import com.lite.jop.demo.security.SimpleUploadSecurityManager;
import com.lite.jop.foundation.annotation.ServiceMapper;
import com.lite.jop.foundation.annotation.ServiceResultHandler;
import com.lite.jop.foundation.annotation.ServiceResultHandlers;
import com.lite.jop.platform.JopServiceMapper;
import com.lite.jop.platform.config.AppSecurityManager;
import com.lite.jop.platform.config.SessionSecurityManager;
import com.lite.jop.platform.config.ThreadPoolExecutor;
import com.lite.jop.platform.config.UploadSecurityManager;
import com.lite.jop.platform.result.ResponseResultHandler;
import com.lite.jop.platform.result.ResultResultHandler;

/**
 * ApiConfig
 *
 * @author LaineyC
 */
@ThreadPoolExecutor
@ServiceResultHandlers({
        @ServiceResultHandler(ResultResultHandler.class),
        @ServiceResultHandler(ResponseResultHandler.class)
})
@ServiceMapper(JopServiceMapper.class)
@UploadSecurityManager(SimpleUploadSecurityManager.class)
@SessionSecurityManager(SimpleSessionSecurityManager.class)
@AppSecurityManager(SimpleAppSecurityManager.class)
public class ApiConfig {
}
