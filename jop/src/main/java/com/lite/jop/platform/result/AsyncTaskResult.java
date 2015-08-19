package com.lite.jop.platform.result;

import com.lite.jop.foundation.*;
import com.lite.jop.foundation.interfaces.ServiceResult;
import com.lite.jop.platform.JopServiceConfig;
import com.lite.jop.platform.JopServiceContext;
import com.lite.jop.platform.config.ThreadPoolExecutor;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * AsyncTaskResult
 *
 * @author LaineyC
 */
public class AsyncTaskResult<R> implements ServiceResult {

    private static java.util.concurrent.ThreadPoolExecutor taskThreadPoolExecutor;

    static{
        JopServiceConfig serviceConfig = ServiceManager.getServiceConfig();
        ServiceAnnotationConfig<ThreadPoolExecutor> threadPoolExecutorConfig = serviceConfig.getThreadPoolExecutor();
        Class<? extends java.util.concurrent.ThreadPoolExecutor> taskThreadPoolExecutorClass = threadPoolExecutorConfig.getAnnotation().value();
        taskThreadPoolExecutor = ServiceManager.getServiceContainer().getInstance(taskThreadPoolExecutorClass);
    }

    private ServiceResultHandlerManager resultHandlerManager = ServiceManager.getServiceContainer().getInstance(ServiceResultHandlerManager.class);

    //超时时间
    private long timeout = 0;

    private Callback<R, R> successCallback;

    private Callback<Throwable, Object> timeoutCallback;

    private Callback<Throwable, Object> errorCallback;

    private AsyncContext asyncContext;

    private Callable callable;

    public AsyncTaskResult(Callable callable){
        this.callable = callable;
    }

    public AsyncTaskResult(Callable callable, long timeout){
        this(callable);
        this.timeout = timeout;
    }

    @Override
    public void execute(ServiceContext serviceContext) {
        JopServiceContext jopServiceContext = (JopServiceContext)serviceContext;
        HttpServletRequest request = jopServiceContext.getHttpServletRequest();
        asyncContext = request.startAsync();
        asyncContext.setTimeout(0);
        Future<R> future = taskThreadPoolExecutor.submit(callable);
        taskThreadPoolExecutor.execute(() -> {
            try {
                R result = timeout > 0 ? future.get(timeout, TimeUnit.MILLISECONDS) : future.get();
                if(successCallback != null) {
                    result = successCallback.call(result);
                }
                resultHandlerManager.handleResult(result, jopServiceContext);
            }
            catch (TimeoutException e) {
                Object result = e;
                if(timeoutCallback != null) {
                     result = timeoutCallback.call(e);
                }
                resultHandlerManager.handleResult(result, jopServiceContext);
            }
            catch (Throwable e) {
                Object result = e;
                if(errorCallback != null) {
                    result = errorCallback.call(e);
                }
                resultHandlerManager.handleResult(result, jopServiceContext);
            }
            finally {
                asyncContext.complete();
            }
        });
    }

    public Callable getCallable() {
        return callable;
    }

    public void setCallable(Callable callable) {
        this.callable = callable;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public Callback<Throwable, Object> getTimeoutCallback() {
        return timeoutCallback;
    }

    public void setTimeoutCallback(Callback<Throwable, Object> timeoutCallback) {
        this.timeoutCallback = timeoutCallback;
    }

    public Callback<Throwable, Object> getErrorCallback() {
        return errorCallback;
    }

    public void setErrorCallback(Callback<Throwable, Object> errorCallback) {
        this.errorCallback = errorCallback;
    }

    public Callback<R, R> getSuccessCallback() {
        return successCallback;
    }

    public void setSuccessCallback(Callback<R, R> successCallback) {
        this.successCallback = successCallback;
    }

}
