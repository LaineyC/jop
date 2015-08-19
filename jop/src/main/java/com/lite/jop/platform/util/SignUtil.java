package com.lite.jop.platform.util;

import com.lite.jop.platform.JopException;
import com.lite.jop.platform.SystemConstant;
import com.lite.jop.platform.ApiException;
import com.lite.jop.platform.config.IgnoreSign;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * SignUtil
 *
 * @author LaineyC
 */
public abstract class SignUtil {

    public static String sign(Map<String, String> params, String secret, Object paramBean){
        Map<String, String> signParams = new HashMap<>(params);
        Class<?> requestClass = paramBean.getClass();
        try{
            BeanInfo beanInfo = Introspector.getBeanInfo(requestClass);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                Method readMethod = pd.getReadMethod();
                String fieldName = pd.getName();
                if (readMethod == null || "class".equals(fieldName)) {
                    continue;
                }
                Field field = getField(requestClass, fieldName);
                if(field == null){
                    continue;
                }
                IgnoreSign ignoreSignField = field.getAnnotation(IgnoreSign.class);
                if (ignoreSignField == null) {
                    ignoreSignField = field.getClass().getAnnotation(IgnoreSign.class);
                }
                if(ignoreSignField != null){
                    continue;
                }
                Object fieldValue = readMethod.invoke(paramBean);
                if(fieldValue ==null){
                    continue;
                }
                signParams.put(pd.getName(), fieldValue.toString());
            }
        }
        catch (Exception e) {
            throw new JopException(e);
        }
        String[] keys = signParams.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        StringBuilder query = new StringBuilder();
        query.append(secret);
        for (String key : keys) {
            String value = signParams.get(key);
            query.append(key).append(value);
        }
        query.append(secret);
        byte[] bytes = sha1(query.toString());
        return byte2hex(bytes);
    }

    private static byte[] md5(String data){
        byte[] bytes;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes(SystemConstant.CHARSET_UTF8));
        }
        catch (Exception e) {
            throw new JopException(e);
        }
        return bytes;
    }

    private static byte[] sha1(String data){
        byte[] bytes;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes(SystemConstant.CHARSET_UTF8));
        }
        catch (Exception e) {
            throw new JopException(e);
        }
        return bytes;
    }

    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    private static Field getField(Class<?> beanClass, String fieldName){
        Field field = null;
        Class<?> target = beanClass;
        do{
            try {
                field = target.getDeclaredField(fieldName);
            }
            catch (NoSuchFieldException e){
                target = target.getSuperclass();
            }
        }
        while(target != null && field == null);
        return field;
    }

}
