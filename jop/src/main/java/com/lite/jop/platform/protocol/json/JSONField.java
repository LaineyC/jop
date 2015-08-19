package com.lite.jop.platform.protocol.json;

import java.lang.annotation.*;

/**
 * JSONField
 *
 * @author LaineyC
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
@Documented
public @interface JSONField {

    public String value();

}
