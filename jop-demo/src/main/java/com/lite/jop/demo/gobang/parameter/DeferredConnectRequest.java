package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.platform.model.JopRequest;

/**
 * HallDetailRequest
 *
 * @author LaineyC
 */
public class DeferredConnectRequest extends JopRequest<DeferredResponse> {
    @Override
    public String serviceMethod() {
        return "deferred.connect";
    }

    @Override
    public Class<DeferredResponse> responseClass() {
        return DeferredResponse.class;
    }

}
