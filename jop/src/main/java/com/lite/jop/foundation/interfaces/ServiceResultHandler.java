package com.lite.jop.foundation.interfaces;

import com.lite.jop.foundation.ServiceContext;

/**
 * ServiceResultHandler
 *
 * @author LaineyC
 */
public interface ServiceResultHandler<T> {

    public void handleResult(T result, ServiceContext serviceContext);

}
