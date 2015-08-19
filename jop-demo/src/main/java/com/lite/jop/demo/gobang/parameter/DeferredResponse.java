package com.lite.jop.demo.gobang.parameter;

import com.lite.jop.platform.model.JopResponse;

/**
 * DeferredResponse
 *
 * @author LaineyC
 */
public abstract class DeferredResponse<T> extends JopResponse<T> {

    public DeferredResponse(){

    }

    public DeferredResponse(T t){
        super(t);
    }

    protected String channelMethod;

    public String getChannelMethod() {
        return channelMethod;
    }

    public void setChannelMethod(String channelMethod) {
        this.channelMethod = channelMethod;
    }

}
