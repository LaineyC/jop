package com.lite.jop.platform.model;

/**
 * Error
 *
 * @author LaineyC
 */
public class Error {

    private String code;

    private String level;

    private String message;

    public Error(){

    }

    public Error(String message) {
        this.message = message;
    }

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Error(String code, String level, String message) {
        this.code = code;
        this.level = level;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
