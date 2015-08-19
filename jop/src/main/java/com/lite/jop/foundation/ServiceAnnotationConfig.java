package com.lite.jop.foundation;

import java.lang.annotation.Annotation;

/**
 * ServiceAnnotationConfig
 *
 * @author LaineyC
 */
public class ServiceAnnotationConfig<A extends Annotation> {

    private A annotation;

    private Class<?> type;

    public ServiceAnnotationConfig(A annotation, Class<?> type) {
        this.annotation = annotation;
        this.type = type;
    }

    public A getAnnotation() {
        return annotation;
    }


    public Class<?> getType() {
        return type;
    }

}
