package com.lite.jop.platform.protocol.json;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.*;

/**
 * JSON
 *
 * @author LaineyC
 */
public abstract class JSON {

    private interface ValueHandler{

        public Object toJsonValue(Object fieldValue, String fieldName);

        public Object toJsonValue(Object fieldValue, int index);

        public Object toJavaValue(JSONObject jsonObject, String fieldName);

        public Object toJavaValue(JSONArray jsonArray, int index);

    }

    private static final Map<Class<?>, ValueHandler> handlerMap = new HashMap<>();

    static{
        handlerMap.put(boolean.class, new ValueHandler() {
            @Override
            public Object toJsonValue(Object fieldValue, String fieldName) {
                return fieldValue;
            }

            @Override
            public Object toJsonValue(Object fieldValue, int index) {
                return fieldValue;
            }

            @Override
            public Object toJavaValue(JSONObject jsonObject, String fieldName) {
                return jsonObject.optBoolean(fieldName);
            }

            @Override
            public Object toJavaValue(JSONArray jsonArray, int index) {
                return jsonArray.optBoolean(index);
            }
        });
        handlerMap.put(Boolean.class, new ValueHandler() {
            @Override
            public Object toJsonValue(Object fieldValue, String fieldName) {
                return fieldValue;
            }

            @Override
            public Object toJsonValue(Object fieldValue, int index) {
                return fieldValue;
            }

            @Override
            public Object toJavaValue(JSONObject jsonObject, String fieldName) {
                return jsonObject.getBoolean(fieldName);
            }

            @Override
            public Object toJavaValue(JSONArray jsonArray, int index) {
                return jsonArray.getBoolean(index);
            }
        });
        handlerMap.put(byte.class, new ValueHandler() {
            @Override
            public Object toJsonValue(Object fieldValue, String fieldName) {
                return fieldValue;
            }

            @Override
            public Object toJsonValue(Object fieldValue, int index) {
                return fieldValue;
            }

            @Override
            public Object toJavaValue(JSONObject jsonObject, String fieldName) {
                return (byte)jsonObject.optInt(fieldName);
            }

            @Override
            public Object toJavaValue(JSONArray jsonArray, int index) {
                return (byte)jsonArray.optInt(index);
            }
        });
        handlerMap.put(Byte.class, new ValueHandler() {
            @Override
            public Object toJsonValue(Object fieldValue, String fieldName) {
                return fieldValue;
            }

            @Override
            public Object toJsonValue(Object fieldValue, int index) {
                return fieldValue;
            }

            @Override
            public Object toJavaValue(JSONObject jsonObject, String fieldName) {
                return (byte)jsonObject.getInt(fieldName);
            }

            @Override
            public Object toJavaValue(JSONArray jsonArray, int index) {
                return (byte)jsonArray.getInt(index);
            }
        });
        handlerMap.put(short.class, new ValueHandler() {
            @Override
            public Object toJsonValue(Object fieldValue, String fieldName) {
                return fieldValue;
            }

            @Override
            public Object toJsonValue(Object fieldValue, int index) {
                return fieldValue;
            }

            @Override
            public Object toJavaValue(JSONObject jsonObject, String fieldName) {
                return (short)jsonObject.optInt(fieldName);
            }

            @Override
            public Object toJavaValue(JSONArray jsonArray, int index) {
                return (short)jsonArray.optInt(index);
            }
        });
        handlerMap.put(Short.class, new ValueHandler() {
            @Override
            public Object toJsonValue(Object fieldValue, String fieldName) {
                return fieldValue;
            }

            @Override
            public Object toJsonValue(Object fieldValue, int index) {
                return fieldValue;
            }

            @Override
            public Object toJavaValue(JSONObject jsonObject, String fieldName) {
                return (short)jsonObject.getInt(fieldName);
            }

            @Override
            public Object toJavaValue(JSONArray jsonArray, int index) {
                return (short)jsonArray.getInt(index);
            }
        });
        handlerMap.put(int.class, new ValueHandler() {
            @Override
            public Object toJsonValue(Object fieldValue, String fieldName) {
                return fieldValue;
            }

            @Override
            public Object toJsonValue(Object fieldValue, int index) {
                return fieldValue;
            }

            @Override
            public Object toJavaValue(JSONObject jsonObject, String fieldName) {
                return jsonObject.optInt(fieldName);
            }

            @Override
            public Object toJavaValue(JSONArray jsonArray, int index) {
                return jsonArray.optInt(index);
            }
        });
        handlerMap.put(Integer.class, new ValueHandler() {
            @Override
            public Object toJsonValue(Object fieldValue, String fieldName) {
                return fieldValue;
            }

            @Override
            public Object toJsonValue(Object fieldValue, int index) {
                return fieldValue;
            }

            @Override
            public Object toJavaValue(JSONObject jsonObject, String fieldName) {
                return jsonObject.getInt(fieldName);
            }

            @Override
            public Object toJavaValue(JSONArray jsonArray, int index) {
                return jsonArray.getInt(index);
            }
        });
        handlerMap.put(long.class, new ValueHandler() {
            @Override
            public Object toJsonValue(Object fieldValue, String fieldName) {
                return fieldValue;
            }

            @Override
            public Object toJsonValue(Object fieldValue, int index) {
                return fieldValue;
            }

            @Override
            public Object toJavaValue(JSONObject jsonObject, String fieldName) {
                return jsonObject.optLong(fieldName);
            }

            @Override
            public Object toJavaValue(JSONArray jsonArray, int index) {
                return jsonArray.optLong(index);
            }
        });
        handlerMap.put(Long.class, new ValueHandler() {
            @Override
            public Object toJsonValue(Object fieldValue, String fieldName) {
                return fieldValue;
            }

            @Override
            public Object toJsonValue(Object fieldValue, int index) {
                return fieldValue;
            }

            @Override
            public Object toJavaValue(JSONObject jsonObject, String fieldName) {
                return jsonObject.getLong(fieldName);
            }

            @Override
            public Object toJavaValue(JSONArray jsonArray, int index) {
                return jsonArray.getLong(index);
            }
        });
        handlerMap.put(float.class, new ValueHandler() {
            @Override
            public Object toJsonValue(Object fieldValue, String fieldName) {
                return fieldValue;
            }

            @Override
            public Object toJsonValue(Object fieldValue, int index) {
                return fieldValue;
            }

            @Override
            public Object toJavaValue(JSONObject jsonObject, String fieldName) {
                return (float)jsonObject.optDouble(fieldName);
            }

            @Override
            public Object toJavaValue(JSONArray jsonArray, int index) {
                return (float)jsonArray.optDouble(index);
            }
        });
        handlerMap.put(Float.class, new ValueHandler() {
            @Override
            public Object toJsonValue(Object fieldValue, String fieldName) {
                return fieldValue;
            }

            @Override
            public Object toJsonValue(Object fieldValue, int index) {
                return fieldValue;
            }

            @Override
            public Object toJavaValue(JSONObject jsonObject, String fieldName) {
                return (float)jsonObject.getDouble(fieldName);
            }

            @Override
            public Object toJavaValue(JSONArray jsonArray, int index) {
                return (float)jsonArray.getDouble(index);
            }
        });
        handlerMap.put(double.class, new ValueHandler() {
            @Override
            public Object toJsonValue(Object fieldValue, String fieldName) {
                return fieldValue;
            }

            @Override
            public Object toJsonValue(Object fieldValue, int index) {
                return fieldValue;
            }

            @Override
            public Object toJavaValue(JSONObject jsonObject, String fieldName) {
                return jsonObject.optDouble(fieldName);
            }

            @Override
            public Object toJavaValue(JSONArray jsonArray, int index) {
                return jsonArray.optDouble(index);
            }
        });
        handlerMap.put(Double.class, new ValueHandler() {
            @Override
            public Object toJsonValue(Object fieldValue, String fieldName) {
                return fieldValue;
            }

            @Override
            public Object toJsonValue(Object fieldValue, int index) {
                return fieldValue;
            }

            @Override
            public Object toJavaValue(JSONObject jsonObject, String fieldName) {
                return jsonObject.getDouble(fieldName);
            }

            @Override
            public Object toJavaValue(JSONArray jsonArray, int index) {
                return jsonArray.getDouble(index);
            }
        });
        handlerMap.put(String.class, new ValueHandler() {
            @Override
            public Object toJsonValue(Object fieldValue, String fieldName) {
                return fieldValue;
            }

            @Override
            public Object toJsonValue(Object fieldValue, int index) {
                return fieldValue;
            }

            @Override
            public Object toJavaValue(JSONObject jsonObject, String fieldName) {
                return jsonObject.getString(fieldName);
            }

            @Override
            public Object toJavaValue(JSONArray jsonArray, int index) {
                return jsonArray.getString(index);
            }
        });
    }

    private JSON(){

    }

    public static String javaBeanToJsonString(Object bean){
        return JSON.javaBeanToJsonObject(bean).toString();
    }

    private static JSONObject javaBeanToJsonObject(Object bean){
        if(bean == null){
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        Class<?> beanClass = bean.getClass();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                Method readMethod = pd.getReadMethod();
                String fieldName = pd.getName();
                if (readMethod == null || "class".equals(fieldName)) {
                    continue;
                }
                Field field = getField(beanClass, fieldName);
                if(field == null){
                    continue;
                }
                JSONField jsonField = field.getAnnotation(JSONField.class);
                if (jsonField != null) {
                    fieldName = jsonField.value();
                }
                Class<?> fieldType = pd.getPropertyType();
                try {
                    ValueHandler valueHandler = handlerMap.get(fieldType);
                    Object fieldValue = readMethod.invoke(bean);
                    Object jsonValue;
                    if(valueHandler != null){
                        jsonValue = valueHandler.toJsonValue(fieldValue, fieldName);
                    }
                    else{
                        if(Collection.class.isAssignableFrom(fieldType)){
                            Class<?> itemType = JSON.getCollectionGenericType(field, 0);
                            jsonValue = JSON.javaCollectionToJsonArray((Collection<?>) fieldValue, itemType);
                        }
                        else if(Map.class.isAssignableFrom(fieldType)){
                            Class<?> itemType = JSON.getCollectionGenericType(field, 1);
                            jsonValue = JSON.javaMapToJsonObject((Map) fieldValue, itemType);
                        }
                        else if(fieldType.isArray()){
                            jsonValue = JSON.javaArrayToJsonArray(fieldValue, fieldType.getComponentType());
                        }
                        else{
                            jsonValue = JSON.javaBeanToJsonObject(fieldValue);
                        }
                    }
                    if(jsonValue != null){
                        jsonObject.put(fieldName, jsonValue);
                    }
                }
                catch(JSONException e){
                    //
                }
            }
        }
        catch (Exception e) {
            throw new JSONException(e);
        }
        return jsonObject;
    }

    private static JSONObject javaMapToJsonObject(Map<String, ?> map, Class<?> itemClass){
        if(map == null){
            return null;
        }
        ValueHandler valueHandler = handlerMap.get(itemClass);
        JSONObject jsonObject = new JSONObject();
        for(Map.Entry<String, ?> entry : map.entrySet()){
            Object jsonValue;
            if(valueHandler != null){
                jsonValue = valueHandler.toJsonValue(entry.getValue(), entry.getKey());
            }
            else{
                jsonValue = JSON.javaBeanToJsonObject(entry.getValue());
            }
            if(jsonValue != null){
                jsonObject.put(entry.getKey(), jsonValue);
            }
        }
        return jsonObject;
    }

    private static JSONArray javaArrayToJsonArray(Object array, Class<?> itemClass){
        if(array == null){
            return null;
        }
        ValueHandler valueHandler = handlerMap.get(itemClass);
        JSONArray jsonArray = new JSONArray();
        int i = 0 ;
        while(true){
            Object jsonValue;
            Object item;
            try {
                item = Array.get(array, i);
            }
            catch (Exception e){
                break;
            }
            if(valueHandler != null){
                jsonValue = valueHandler.toJsonValue(item, i);
            }
            else if(item.getClass().isArray()){
                jsonValue = JSON.javaArrayToJsonArray(Array.get(item, i), item.getClass());
            }
            else{
                jsonValue = JSON.javaBeanToJsonObject(Array.get(item, i));
            }
            if(jsonValue != null){
                jsonArray.put(jsonValue);
            }
            i++;
        }
        return jsonArray;
    }

    private static JSONArray javaCollectionToJsonArray(Collection<?> collection, Class<?> itemClass){
        if(collection == null){
            return null;
        }
        ValueHandler valueHandler = handlerMap.get(itemClass);
        JSONArray jsonArray = new JSONArray();
        int i = 0 ;
        for(Object item : collection){
            Object jsonValue;
            if(valueHandler != null){
                jsonValue = valueHandler.toJsonValue(item, i);
            }
            else{
                jsonValue = JSON.javaBeanToJsonObject(item);
            }
            if(jsonValue != null){
                jsonArray.put(jsonValue);
            }
            i++;
        }
        return jsonArray;
    }

    public static <T> T jsonStringToJavaBean(String json, Class<T> beanClass){
        JSONTokener jsonTokener = new JSONTokener(json);
        return JSON.jsonObjectToJavaBean((JSONObject) jsonTokener.nextValue(), beanClass);
    }

    private static Object jsonArrayToJavaArray(JSONArray jsonArray, Class<?> itemClass){
        if(jsonArray == null){
            return null;
        }
        Object array =  Array.newInstance(itemClass , jsonArray.length());
        ValueHandler valueHandler = handlerMap.get(itemClass);
        for(int i = 0 ; i < jsonArray.length() ; i++){
            Object item;
            if(valueHandler != null){
                item = valueHandler.toJavaValue(jsonArray, i);
            }
            else if(itemClass.isArray()){
                item = JSON.jsonArrayToJavaArray(jsonArray.getJSONArray(i), itemClass.getComponentType());
            }
            else{
                item = JSON.jsonObjectToJavaBean(jsonArray.optJSONObject(i), itemClass);
            }
            Array.set(array, i, item);
        }
        return array;
    }

    private static <T> List<T> jsonArrayToJavaCollection(JSONArray jsonArray, Class<T> itemClass){
        if(jsonArray == null){
            return null;
        }
        List<T> list = new ArrayList<>();
        ValueHandler valueHandler = handlerMap.get(itemClass);
        for(int i = 0 ; i < jsonArray.length() ; i ++){
            Object item;
            if(valueHandler != null){
                item = valueHandler.toJavaValue(jsonArray, i);
            }
            else{
                item = JSON.jsonObjectToJavaBean(jsonArray.optJSONObject(i), itemClass);
            }
            list.add((T)item);
        }
        return list;
    }

    private static <T> Map<String, T> jsonObjectToJavaMap(JSONObject jsonObject, Class<T> itemClass){
        if(jsonObject == null){
            return null;
        }
        Map<String, T> map = new HashMap<>();
        ValueHandler valueHandler = handlerMap.get(itemClass);
        for(String key : jsonObject.keySet()){
            Object item;
            if(valueHandler != null){
                item = valueHandler.toJavaValue(jsonObject, key);
            }
            else{
                item = JSON.jsonObjectToJavaBean(jsonObject.optJSONObject(key), itemClass);
            }
            map.put(key, (T)item);
        }
        return map;
    }

    private static <T> T jsonObjectToJavaBean(JSONObject jsonObject, Class<T> beanClass){
        if(jsonObject == null){
            return null;
        }
        T bean;
        try {
            bean = beanClass.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                Method writeMethod = pd.getWriteMethod();
                if (writeMethod == null) {
                    continue;
                }
                String fieldName = pd.getName();
                Field field = getField(beanClass, fieldName);
                if(field == null){
                    continue;
                }
                JSONField jsonField = field.getAnnotation(JSONField.class);
                if (jsonField != null) {
                    fieldName = jsonField.value();
                }
                Class<?> fieldType = pd.getPropertyType();
                try {
                    ValueHandler valueHandler = handlerMap.get(fieldType);
                    Object fieldValue;
                    if(valueHandler != null){
                        fieldValue = valueHandler.toJavaValue(jsonObject, fieldName);
                    }
                    else{
                        if(Collection.class.isAssignableFrom(fieldType)){
                            Class<?> itemType = JSON.getCollectionGenericType(field, 0);
                            fieldValue = pd.getReadMethod().invoke(bean);
                            List<?> list = JSON.jsonArrayToJavaCollection(jsonObject.optJSONArray(fieldName), itemType);
                            if(list != null){
                                for(Object item : list){
                                    ((Collection)fieldValue).add(item);
                                }
                            }
                        }
                        else if(Map.class.isAssignableFrom(fieldType)){
                            Class<?> itemType = JSON.getCollectionGenericType(field, 1);
                            fieldValue = pd.getReadMethod().invoke(bean);
                            Map<String, ?> map = JSON.jsonObjectToJavaMap(jsonObject.optJSONObject(fieldName), itemType);
                            if(map != null) {
                                for (Map.Entry<String, ?> entry : map.entrySet()) {
                                    ((Map) fieldValue).put(entry.getKey(), entry.getValue());
                                }
                            }
                        }
                        else if(fieldType.isArray()){
                            fieldValue = JSON.jsonArrayToJavaArray(jsonObject.optJSONArray(fieldName), fieldType.getComponentType());
                        }
                        else{
                            fieldValue = JSON.jsonObjectToJavaBean(jsonObject.optJSONObject(fieldName), fieldType);
                        }
                    }
                    writeMethod.invoke(bean, fieldValue);
                }
                catch(JSONException e){
                    //
                }
            }
        }
        catch (Exception e) {
            throw new JSONException(e);
        }
        return bean;
    }

    private static Class<?> getCollectionGenericType(Field field, int index){
        Type fieldGenericType = field.getGenericType();
        if (fieldGenericType instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) fieldGenericType;
            Type[] genericTypes = paramType.getActualTypeArguments();
            if (genericTypes != null && genericTypes.length > index) {
                if (genericTypes[index] instanceof Class<?>) {
                    return(Class<?>) genericTypes[index];
                }
            }
        }
        return null;
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
