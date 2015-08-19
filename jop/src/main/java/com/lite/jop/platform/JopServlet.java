package com.lite.jop.platform;

import com.lite.jop.foundation.ServiceAnnotationConfig;
import com.lite.jop.foundation.ServiceConfig;
import com.lite.jop.foundation.ServiceManager;
import com.lite.jop.foundation.ServiceRouter;
import com.lite.jop.platform.config.ThreadPoolExecutor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JopServlet
 *
 * @author LaineyC
 */
public class JopServlet extends HttpServlet {

    private ServiceRouter serviceRouter;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        long start = System.currentTimeMillis();

        JopServiceContext jopServiceContext = new JopServiceContext();
        jopServiceContext.setHttpServletRequest(request);
        jopServiceContext.setHttpServletResponse(response);

        serviceRouter.route(jopServiceContext);

        long end = System.currentTimeMillis();
        System.out.println("框架处理时间：" + (end - start) + "ms");
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        String scanPackage = servletConfig.getInitParameter("scanPackage");
        JopServiceConfig serviceConfig = new JopServiceConfig(scanPackage);
        ServiceManager.setServiceConfig(serviceConfig);
        this.serviceRouter = ServiceManager.getServiceContainer().getInstance(ServiceRouter.class);
    }

    @Override
    public void destroy() {
        JopServiceConfig serviceConfig= ServiceManager.getServiceConfig();
        ServiceAnnotationConfig<ThreadPoolExecutor> threadPoolExecutorConfig = serviceConfig.getThreadPoolExecutor();
        Class<? extends java.util.concurrent.ThreadPoolExecutor> taskThreadPoolExecutorClass = threadPoolExecutorConfig.getAnnotation().value();
        ServiceManager.getServiceContainer().getInstance(taskThreadPoolExecutorClass).shutdownNow();
    }
}

