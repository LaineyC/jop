package com.lite.jop.platform;

import com.lite.jop.platform.model.Error;

import java.util.ArrayList;
import java.util.List;

/**
 * ApiException
 *
 * @author LaineyC
 */
public class ApiException extends RuntimeException  {

    private List<Error> errors = new ArrayList<>();

    public ApiException(Error... errors) {
        addErrors(errors);
    }

    private void addErrors(Error... errors){
        if(errors != null){
            for(Error error : errors){
                this.errors.add(error);
            }
        }
    }

    public List<Error> getErrors(){
        return errors;
    }

    public boolean hasError(){
        return !errors.isEmpty();
    }

}