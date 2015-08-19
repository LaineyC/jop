package com.lite.jop.foundation.annotation;

import java.lang.annotation.*;

/**
 * ServiceMethod
 *
 * @author LaineyC
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceMethod {

}
