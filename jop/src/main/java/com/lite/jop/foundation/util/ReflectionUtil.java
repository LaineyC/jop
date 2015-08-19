package com.lite.jop.foundation.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtil {
	
	/**
	 * 反射获取父类的泛型参数的实际类型。
	 * @param clazz   需要反射的类，该类继承泛型父类。
	 * @param index   泛型参数所在索引，从0开始。
	 * @return 泛型参数的实际类型，如果没有实现ParameterizedType接口，既不支持泛型，直接返回<code>null</code>
	 */
	public static Class getSuperClassGenericsType(Class clazz, int index){
		ParameterizedType parameterizedType = getParameterizedType(clazz, true);
		if(parameterizedType == null){
			return null;
		}
		Type[] params = parameterizedType.getActualTypeArguments();
		if (!(params[index] instanceof Class)) {
			return null;
		}   
		return (Class)params[index];
	}
	
	/**
	 * 反射获取父类的泛型参数
	 * @param clazz	需要反射的类，该类继承泛型父类。
	 * @param deep	是否递归查找，如果父类不是泛型类则继续查找，如果没有泛型父类返回null
	 * @return 泛型参数类型
	 */
	public static ParameterizedType getParameterizedType(Class clazz, boolean deep){
		if(clazz == null){
			return null;
		}
		Type genType = clazz.getGenericSuperclass();
		if( genType instanceof ParameterizedType ){
			return (ParameterizedType)genType;            
		}
		if( deep ){
			return getParameterizedType(clazz.getSuperclass(), true);
		}
		return null;
	}

    public static Class getSuperClassGenericsType(Class clazz, Class interFace, int index){
        ParameterizedType parameterizedType = getParameterizedType(clazz, interFace, true);
        if(parameterizedType == null){
            return null;
        }
        Type[] params = parameterizedType.getActualTypeArguments();
        if (!(params[index] instanceof Class)) {
            return null;
        }
        return (Class)params[index];
    }

    public static ParameterizedType getParameterizedType(Class clazz, Class interFace,  boolean deep){
        if(clazz == null){
            return null;
        }
        Type[] genericInterfaces  = clazz.getGenericInterfaces();
        if(genericInterfaces != null && genericInterfaces.length > 0){
            for(Type type : genericInterfaces){
                if( type instanceof ParameterizedType ){
                    ParameterizedType parameterizedType = (ParameterizedType)type;
                    if(parameterizedType.getRawType().equals(interFace)){
                        return parameterizedType;
                    }
                }
            }
        }
        if( deep ){
            return getParameterizedType(clazz.getSuperclass(), true);
        }
        return null;
    }

}

