package com.lite.jop.platform.result;

import com.lite.jop.foundation.ServiceContext;
import com.lite.jop.foundation.interfaces.ServiceResult;
import com.lite.jop.foundation.interfaces.ServiceResultHandler;

/**
 * ResultResultHandler
 *
 * @author LaineyC
 */
public class ResultResultHandler implements ServiceResultHandler<ServiceResult> {

    public void handleResult(ServiceResult result, ServiceContext serviceContext){
        result.execute(serviceContext);
    }

}
