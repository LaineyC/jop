package com.lite.jop.platform.model;

/**
 * SystemParameter
 *
 * @author LaineyC
 */
public class SystemParameter {

    private String method;

    private String version;

    private String app;

    private String session;

    private String sign;

    public SystemParameter(){

    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
