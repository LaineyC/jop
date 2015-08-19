package com.lite.jop.platform.result;

import com.lite.jop.foundation.*;
import com.lite.jop.foundation.interfaces.ServiceResult;
import com.lite.jop.platform.JopServiceConfig;
import com.lite.jop.platform.JopException;
import com.lite.jop.platform.JopServiceContext;
import com.lite.jop.platform.config.ThreadPoolExecutor;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/***
 *
 * DeferredResult延时操作
 *
 * @author LaineyC
 * @param <R> 预期返回的类型
 */
public class DeferredResult<R> implements ServiceResult {

    private static java.util.concurrent.ThreadPoolExecutor taskThreadPoolExecutor;

    static{
        JopServiceConfig serviceConfig = ServiceManager.getServiceConfig();
        ServiceAnnotationConfig<ThreadPoolExecutor> threadPoolExecutorConfig = serviceConfig.getThreadPoolExecutor();
        Class<? extends java.util.concurrent.ThreadPoolExecutor> taskThreadPoolExecutorClass = threadPoolExecutorConfig.getAnnotation().value();
        taskThreadPoolExecutor = ServiceManager.getServiceContainer().getInstance(taskThreadPoolExecutorClass);
    }

    private ServiceResultHandlerManager resultHandlerManager = ServiceManager.getServiceContainer().getInstance(ServiceResultHandlerManager.class);

    private R result;

    private Object notify;

    private long timeout = 0;

    private Callback<R, R> successCallback;

    private Callback<Throwable, Object> timeoutCallback;

    private Callback<Throwable, Object> errorCallback;

    private Callback<Object, Object> progressCallback;

    private AsyncContext asyncContext;

    private HttpServletResponse httpServletResponse;

    private HttpServletRequest httpServletRequest;

    private JopServiceContext jopServiceContext;

    public DeferredResult() {

    }

    public DeferredResult(long timeout) {
        this();
        this.timeout = timeout;
    }

    @Override
    public void execute(ServiceContext serviceContext) {
        this.jopServiceContext = (JopServiceContext)serviceContext;
        httpServletResponse = jopServiceContext.getHttpServletResponse();
        httpServletRequest = jopServiceContext.getHttpServletRequest();
        asyncContext = httpServletRequest.startAsync();
        asyncContext.setTimeout(0);
        Future<R> future = taskThreadPoolExecutor.submit(() -> {
            synchronized(this){
                while(!hasResult()){
                    this.wait();
                    if(notify != null) {
                        if (progressCallback != null) {
                            notify = progressCallback.call(notify);
                        }
                        resultHandlerManager.handleResult(notify, jopServiceContext);
                        notify = null;
                    }
                }
            }
            return result;
        });
        taskThreadPoolExecutor.execute(() -> {
            try {
                R result = timeout > 0 ? future.get(timeout, TimeUnit.MILLISECONDS) : future.get();
                if(committed){
                    return;
                }
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
                committed = true;
                asyncContext.complete();
            }
        });
    }

    //解决
    public void resolveResult(R result){
        synchronized(this){
            if(!isCommitted()){
                this.result = result;
                this.notify();
            }
            else{
                throw new JopException("响应已经完成！");
            }
        }
    }

    //通知
    public void notifyResult(Object notify){
        synchronized(this) {
            if (!isCommitted()) {
                this.notify = notify;
                this.notify();
            }
            else{
                throw new JopException("响应已经完成！");
            }
        }
    }

    private boolean committed = false;

    public void cancel(){
        synchronized(this) {
            if (!isCommitted()) {
                committed = true;
                this.notify();
            }
        }
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public Callback<R, R> getSuccessCallback() {
        return successCallback;
    }

    public void setSuccessCallback(Callback<R, R> successCallback) {
        this.successCallback = successCallback;
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

    public Callback<Object, Object> getProgressCallback() {
        return progressCallback;
    }

    public void setProgressCallback(Callback<Object, Object> progressCallback) {
        this.progressCallback = progressCallback;
    }

    public boolean hasResult() {
        return this.result != null;
    }

    public boolean isCommitted(){
        return httpServletResponse.isCommitted() || hasResult() || committed;
    }

    public Object getResult() {
        return this.result;
    }

}
