package com.lite.jop.platform.result;

/**
 * Callback
 *
 * @author LaineyC
 */
public interface Callback<P, V> {

    public V call(P p);

}
