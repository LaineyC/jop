package com.lite.jop.foundation.util;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * AnnotationUtil
 *
 * @author LaineyC
 */
public class AnnotationUtil {

    public static <A extends Annotation> List<A> findAnnotation(Method method, Class<A> annotationClass){
        return findAnnotation(method.getAnnotations(), annotationClass);
    }

    public static <A extends Annotation> List<A> findAnnotation(Class<?> clazz, Class<A> annotationClass){
        return findAnnotation(clazz.getAnnotations(), annotationClass);
    }

    private static <A extends Annotation> List<A> findAnnotation(Annotation[] annotations, Class<A> annotationClass){
        Repeatable repeatable = annotationClass.getAnnotation(Repeatable.class);
        Class<? extends Annotation> repeatableAnnotation = null;
        if(repeatable != null){
            repeatableAnnotation = repeatable.value();
        }
        List<A> list = new ArrayList<>();
        for(Annotation annotation : annotations){
            if(annotation instanceof Documented ||
                    annotation instanceof Retention ||
                    annotation instanceof Target){
                continue;
            }
            if(repeatableAnnotation != null && repeatableAnnotation.isInstance(annotation)){
                try {
                    A[] as = (A[])repeatableAnnotation.getMethod("value").invoke(annotation);
                    for(A a : as){
                        list.add(a);
                    }
                }
                catch (Exception ex){
                    throw new RuntimeException(ex);
                }
                continue;
            }
            if(annotationClass.isInstance(annotation)){
                list.add((A)annotation);
            }
            list.addAll(findAnnotation(annotation.annotationType(), annotationClass));
        }
        return list;
    }

    public static <A extends Annotation> boolean hasAnnotation(Method method, Class<A> annotationClass){
        return hasAnnotation(method.getAnnotations(), annotationClass);
    }

    public static <A extends Annotation> boolean hasAnnotation(Class<?> clazz, Class<A> annotationClass){
        return hasAnnotation(clazz.getAnnotations(), annotationClass);
    }

    private static <A extends Annotation> boolean hasAnnotation(Annotation[] annotations, Class<A> annotationClass){
        Repeatable repeatable = annotationClass.getAnnotation(Repeatable.class);
        Class<? extends Annotation> repeatableAnnotation = null;
        if(repeatable != null){
            repeatableAnnotation = repeatable.value();
        }
        for(Annotation annotation : annotations){
            if(annotation instanceof Documented ||
                    annotation instanceof Retention ||
                    annotation instanceof Target){
                continue;
            }
            if(repeatableAnnotation != null && repeatableAnnotation.isInstance(annotation)){
                return true;
            }
            if(annotationClass.isInstance(annotation)){
                return true;
            }
            if(hasAnnotation(annotation.annotationType(), annotationClass)){
                return true;
            }
        }
        return false;
    }


}
