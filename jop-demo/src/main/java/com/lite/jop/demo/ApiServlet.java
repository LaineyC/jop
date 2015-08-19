package com.lite.jop.demo;

import com.lite.jop.platform.JopServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * ApiServlet
 *
 * @author LaineyC
 */
@WebServlet(name = "apiServlet", urlPatterns = "/api", initParams = @WebInitParam(name = "scanPackage", value = "com.lite.jop.demo"), asyncSupported = true)
public class ApiServlet extends JopServlet {

}
