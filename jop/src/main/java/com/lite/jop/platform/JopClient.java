package com.lite.jop.platform;

import com.lite.jop.platform.model.JopRequest;
import com.lite.jop.platform.model.JopResponse;
import com.lite.jop.platform.protocol.json.JSON;
import com.lite.jop.platform.util.SignUtil;
import com.lite.jop.platform.util.WebUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * JopClient
 *
 * @author LaineyC
 */
public class JopClient {

    private static final String APP = SystemConstant.APP;

    private static final String METHOD = SystemConstant.METHOD;

    private static final String VERSION = SystemConstant.VERSION;

    private static final String SIGN = SystemConstant.SIGN;

    private static final String SESSION =  SystemConstant.SESSION;

    private String url;

    private String app;

    private String secret;

    private String session;

    private int connectTimeout = 3000;

    private int readTimeout = 15000;

    public JopClient(String url, String app, String secret) {
        this.url = url;
        this.app = app;
        this.secret = secret;
    }

    public JopClient(String url, String app, String secret, int connectTimeout, int readTimeout) {
        this(url, app, secret);
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    public <T extends JopResponse> T execute(JopRequest<T> request){
        return execute(request, null);
    }

    public <T extends JopResponse> T execute(JopRequest<T> request, String version)  {
        byte[] content;
        try {
            content = JSON.javaBeanToJsonString(request).getBytes(SystemConstant.CHARSET_UTF8);
        }
        catch (Exception e){
            throw new JopException(e);
        }
        Map<String, String> protocalParams = new HashMap<>();
        protocalParams.put(METHOD, request.serviceMethod());
        if(version != null){
            protocalParams.put(VERSION, version);
        }
        protocalParams.put(APP, app);
        if(session != null){
            protocalParams.put(SESSION, session);
        }
        protocalParams.put(SIGN, SignUtil.sign(protocalParams, secret, request));
        String responseText;
        try {
            responseText = WebUtil.doPost(url, protocalParams, content, connectTimeout, readTimeout);
        }
        catch (Exception e){
            throw new JopException(e);
        }
        return JSON.jsonStringToJavaBean(responseText, request.responseClass());
    }

    public void setSession(String session) {
        this.session = session;
    }

}
