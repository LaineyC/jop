package com.lite.jop.foundation;

/**
 * ServiceResultNuLLException
 *
 * @author LaineyC
 */
public class ServiceResultNullException extends RuntimeException {

    public ServiceResultNullException() {
    }

    public ServiceResultNullException(String message) {
        super(message);
    }

    public ServiceResultNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceResultNullException(Throwable cause) {
        super(cause);
    }

}
