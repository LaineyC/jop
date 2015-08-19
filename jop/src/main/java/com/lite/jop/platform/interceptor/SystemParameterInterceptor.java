package com.lite.jop.platform.interceptor;

import com.lite.jop.foundation.ServiceContext;
import com.lite.jop.foundation.ServiceException;
import com.lite.jop.foundation.ServiceProxy;
import com.lite.jop.foundation.interfaces.ServiceInterceptor;
import com.lite.jop.platform.JopServiceContext;
import com.lite.jop.platform.SystemConstant;
import com.lite.jop.platform.model.SystemParameter;

import javax.servlet.http.HttpServletRequest;

/**
 * ParameterInjectInterceptor
 *
 * @author LaineyC
 */
public class SystemParameterInterceptor implements ServiceInterceptor {

    @Override
    public Object intercept(ServiceContext serviceContext) {
        System.out.println("平台参数拦截器---开始");

        JopServiceContext jopServiceContext = (JopServiceContext)serviceContext;
        HttpServletRequest request = jopServiceContext.getHttpServletRequest();
        SystemParameter systemParameter = new SystemParameter();
        systemParameter.setApp(request.getParameter(SystemConstant.APP));
        systemParameter.setMethod(request.getParameter(SystemConstant.METHOD));
        systemParameter.setSession(request.getParameter(SystemConstant.SESSION));
        systemParameter.setSign(request.getParameter(SystemConstant.SIGN));
        systemParameter.setVersion(request.getParameter(SystemConstant.VERSION));
        jopServiceContext.setSystemParameter(systemParameter);

        ServiceProxy serviceProxy = serviceContext.getServiceProxy();
        Object result = serviceProxy.progress();

        System.out.println("平台参数拦截器---结束");
        return result;
    }

}
