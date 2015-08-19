package com.lite.jop.platform;

import com.lite.jop.foundation.ServiceContext;
import com.lite.jop.foundation.interfaces.ServiceMapper;
import com.lite.jop.platform.config.Service;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * JopServiceMapper
 *
 * @author LaineyC
 */
public class JopServiceMapper implements ServiceMapper {

    @Override
    public Object mapping(ServiceContext serviceContext) {
        JopServiceContext jopServiceContext = (JopServiceContext)serviceContext;
        HttpServletRequest request = jopServiceContext.getHttpServletRequest();
        String method = request.getParameter("method");
        String version = request.getParameter("version");
        version = version == null ? "" : version;
        return method + "/" + version;
    }

    @Override
    public Object index(Method serviceMethod) {
        Service service = serviceMethod.getAnnotation(Service.class);
        String method = service.method();
        String version = service.version();
        return method + "/" + version;
    }

}