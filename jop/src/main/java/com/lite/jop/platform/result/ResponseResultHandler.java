package com.lite.jop.platform.result;

import com.lite.jop.foundation.ServiceContext;
import com.lite.jop.foundation.interfaces.ServiceResultHandler;
import com.lite.jop.platform.JopException;
import com.lite.jop.platform.JopServiceContext;
import com.lite.jop.platform.SystemConstant;
import com.lite.jop.platform.model.JopResponse;
import com.lite.jop.platform.protocol.json.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * ResponseResultHandler
 *
 * @author LaineyC
 */
public class ResponseResultHandler implements ServiceResultHandler<JopResponse> {

    public void handleResult(JopResponse result, ServiceContext serviceContext){
        JopServiceContext jopServiceContext = (JopServiceContext)serviceContext;
        HttpServletResponse response = jopServiceContext.getHttpServletResponse();
        response.setContentType("application/json;charset=" + SystemConstant.CHARSET_UTF8);
        try {
            PrintWriter out = response.getWriter();
            out.print(JSON.javaBeanToJsonString(result));
            out.flush();
        }
        catch(Exception e){
            throw new JopException(e);
        }
    }

}
