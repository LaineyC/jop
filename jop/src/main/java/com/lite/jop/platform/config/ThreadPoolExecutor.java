package com.lite.jop.platform.config;

import com.lite.jop.platform.TaskThreadPoolExecutor;
import java.lang.annotation.*;

/**
 * ThreadPoolExecutor
 *
 * @author LaineyC
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ThreadPoolExecutor {

    public Class<? extends java.util.concurrent.ThreadPoolExecutor> value() default TaskThreadPoolExecutor.class;

}
