package com.lite.jop.platform.result;

import com.lite.jop.foundation.ServiceContext;
import com.lite.jop.foundation.interfaces.ServiceResult;
import com.lite.jop.platform.JopException;
import com.lite.jop.platform.JopServiceContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * ForwardResult
 *
 * @author LaineyC
 */
public class ForwardResult implements ServiceResult {

    private String url;

    private Map<String, Object> attributes;

    public ForwardResult(String url){
        this.url = url;
    }

    public ForwardResult(String url, Map<String, Object> attributes){
        this.url = url;
        this.attributes = attributes;
    }

    @Override
    public void execute(ServiceContext serviceContext) {
        try {
            JopServiceContext jopServiceContext = (JopServiceContext)serviceContext;
            HttpServletRequest request = jopServiceContext.getHttpServletRequest();
            HttpServletResponse response = jopServiceContext.getHttpServletResponse();
            if(attributes != null){
                attributes.entrySet().forEach(entry -> request.setAttribute(entry.getKey(), entry.getValue()));
            }
            request.getServletContext().getRequestDispatcher(url).forward(request, response);
        }
        catch (Exception e){
            throw new JopException(e);
        }

    }
}
