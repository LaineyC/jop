package com.lite.jop.platform.model;


/**
 * JopRequest
 *
 * @author LaineyC
 */
public abstract class JopRequest<R extends JopResponse>{

    public abstract String serviceMethod();

    public abstract Class<R> responseClass();

}
