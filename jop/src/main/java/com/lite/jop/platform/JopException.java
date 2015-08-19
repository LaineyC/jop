package com.lite.jop.platform;

/**
 * JopException
 *
 * @author LaineyC
 */
public class JopException extends RuntimeException  {

    public JopException(String message) {
        super(message);
    }

    public JopException(String message, Throwable cause) {
        super(message, cause);
    }

    public JopException(Throwable cause) {
        super(cause);
    }

}