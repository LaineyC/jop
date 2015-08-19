/**
 * package-info：<br/>
 * foundation包主要定义了服务的匹配流程 并没有Servlet的API<br/>
 * 如何匹配服务方法 现实ServiceMapper<br/>
 * 匹配后如何处理 实现ServiceInterceptor 通过返回值自行处理<br/>
 * 所有的对象都是单例 启动需要实现ServiceContainer
 * 注解了ServiceProvide表示一个Bean
 * 注解了ServiceMethod表示匹配方法
 * 注解ServiceListener，ServiceInterceptor 直接注解在类和方法上和ServiceProvide，ServiceMethod注解
 * @author LaineyC
 */
package com.lite.jop.foundation;