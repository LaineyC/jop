package com.lite.jop.demo.security;

import java.util.HashMap;
import java.util.Map;

/**
 * SimpleSession
 *
 * @author LaineyC
 */
public class SimpleSession {

    private Map<String, Object> attributes = new HashMap<>();

    public <T> T removeAttribute(String name){
        return (T)attributes.remove(name);
    }

    public <T> T getAttribute(String name){
        return (T)attributes.get(name);
    }

    public void setAttribute(String name, Object value){
        attributes.put(name, value);
    }

}
