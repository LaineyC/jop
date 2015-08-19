package com.lite.jop.foundation.interfaces;

/**
 * ServiceContainer
 * 所有bean都设计单例
 * @author LaineyC
 */
public interface ServiceContainer{

    public <T> T getInstance(Class<T> clazz);

}
