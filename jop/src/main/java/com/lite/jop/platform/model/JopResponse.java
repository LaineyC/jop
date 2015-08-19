package com.lite.jop.platform.model;

import java.util.ArrayList;
import java.util.List;

/**
 * JopResponse
 *
 * @author LaineyC
 */
public class JopResponse<T>{

    private List<Error> errors = new ArrayList<>();

    private T body;

    public JopResponse(){

    }

    public JopResponse(T body){
        this.body = body;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public  T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

}
