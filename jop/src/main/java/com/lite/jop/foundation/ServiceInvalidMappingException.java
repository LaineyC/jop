package com.lite.jop.foundation;

/**
 * ServiceInvalidMappingException
 *
 * @author LaineyC
 */
public class ServiceInvalidMappingException extends RuntimeException {

    public ServiceInvalidMappingException() {
    }

    public ServiceInvalidMappingException(String message) {
        super(message);
    }

    public ServiceInvalidMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceInvalidMappingException(Throwable cause) {
        super(cause);
    }

}
