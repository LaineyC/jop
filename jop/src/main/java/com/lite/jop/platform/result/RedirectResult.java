package com.lite.jop.platform.result;

import com.lite.jop.foundation.ServiceContext;
import com.lite.jop.foundation.interfaces.ServiceResult;
import com.lite.jop.platform.JopException;
import com.lite.jop.platform.JopServiceContext;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * RedirectResult
 *
 * @author LaineyC
 */
public class RedirectResult implements ServiceResult {

    private String url;

    private Map<String, Object> parameters;

    public RedirectResult(String url){
        this.url = url;
    }

    public RedirectResult(String url, Map<String, Object> parameters){
        this.url = url;
        this.parameters = parameters;
    }

    @Override
    public void execute(ServiceContext serviceContext) {
        try {
            JopServiceContext jopServiceContext = (JopServiceContext)serviceContext;
            HttpServletResponse response = jopServiceContext.getHttpServletResponse();
            if(parameters != null){
                url += url.contains("?") ? "&" : "?";
                parameters.entrySet().forEach(e -> url += e.getKey() + "=" + e.getValue() + "&");
                url = url.substring(0, url.length() - 2);
            }
            response.sendRedirect(url);
        }
        catch (Exception e){
            throw new JopException(e);
        }
    }

}
