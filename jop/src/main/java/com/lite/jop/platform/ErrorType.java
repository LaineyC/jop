package com.lite.jop.platform;

import com.lite.jop.platform.model.Error;
import java.util.EnumMap;

/**
 * ErrorType
 *
 * @author LaineyC
 */
public enum ErrorType {

    SERVICE_CURRENTLY_UNAVAILABLE,
    INSUFFICIENT_ISV_PERMISSIONS,
    INSUFFICIENT_USER_PERMISSIONS,
    UPLOAD_FAIL,
    HTTP_ACTION_NOT_ALLOWED,
    INVALID_ENCODING,
    FORBIDDEN_REQUEST,
    METHOD_OBSOLETED,
    BUSINESS_LOGIC_ERROR,
    MISSING_SESSION,
    INVALID_SESSION,
    MISSING_APP_KEY,
    INVALID_APP_KEY,
    MISSING_SIGNATURE,
    INVALID_SIGNATURE,
    MISSING_METHOD,
    INVALID_METHOD,
    MISSING_VERSION,
    INVALID_VERSION,
    UNSUPPORTED_VERSION,
    INVALID_FORMAT,
    MISSING_REQUIRED_ARGUMENTS,
    INVALID_ARGUMENTS,
    EXCEED_USER_INVOKE_LIMITED,
    EXCEED_SESSION_INVOKE_LIMITED,
    EXCEED_APP_INVOKE_LIMITED,
    EXCEED_APP_INVOKE_FREQUENCY_LIMITED;

    private static EnumMap<ErrorType, Error> errorCodeMap = new EnumMap<>(ErrorType.class);

    static {
        /**
         1	服务不可用	    20	缺少sessionId参数	29	非法的版本参数
         2	开发者权限不足	21	无效的sessionId参数	30	不支持的版本号
         3	用户权限不足	22	缺少appKey参数	    31	无效报文格式类型
         4	图片上传失败	23	无效的appKey参数	32	缺少必选参数
         5	HTTP方法被禁止	24	缺少签名参数	    33	非法的参数
         6	编码错误	    25	无效签名	        34	用户调用服务的次数超限
         7	请求被禁止	    26	缺少方法名参数	    35	会话调用服务的次数超限
         8	服务已经作废	27	不存在的方法名	    36	应用调用服务的次数超限
         9	业务逻辑出错	28	缺少版本参数	    37	应用调用服务的频率超限
         */
      /*  errorCodeMap.put(ErrorType.SERVICE_CURRENTLY_UNAVAILABLE, "1");
        errorCodeMap.put(ErrorType.INSUFFICIENT_ISV_PERMISSIONS, "2");
        errorCodeMap.put(ErrorType.INSUFFICIENT_USER_PERMISSIONS, "3");
        errorCodeMap.put(ErrorType.UPLOAD_FAIL, "4");
        errorCodeMap.put(ErrorType.HTTP_ACTION_NOT_ALLOWED, "5");
        errorCodeMap.put(ErrorType.INVALID_ENCODING, "6");
        errorCodeMap.put(ErrorType.FORBIDDEN_REQUEST, "7");
        errorCodeMap.put(ErrorType.METHOD_OBSOLETED, "8");
        errorCodeMap.put(ErrorType.BUSINESS_LOGIC_ERROR, "9");
        errorCodeMap.put(ErrorType.MISSING_SESSION, "20");
        errorCodeMap.put(ErrorType.INVALID_SESSION, "21");
        errorCodeMap.put(ErrorType.MISSING_APP_KEY, "22");
        errorCodeMap.put(ErrorType.INVALID_APP_KEY, "23");
        errorCodeMap.put(ErrorType.MISSING_SIGNATURE, "24");
        errorCodeMap.put(ErrorType.INVALID_SIGNATURE, "25");
        errorCodeMap.put(ErrorType.MISSING_METHOD, "26");
        errorCodeMap.put(ErrorType.INVALID_METHOD, "27");
        errorCodeMap.put(ErrorType.MISSING_VERSION, "28");
        errorCodeMap.put(ErrorType.INVALID_VERSION, "29");
        errorCodeMap.put(ErrorType.UNSUPPORTED_VERSION, "30");
        errorCodeMap.put(ErrorType.INVALID_FORMAT, "31");
        errorCodeMap.put(ErrorType.MISSING_REQUIRED_ARGUMENTS, "32");
        errorCodeMap.put(ErrorType.INVALID_ARGUMENTS, "33");
        errorCodeMap.put(ErrorType.EXCEED_USER_INVOKE_LIMITED, "34");
        errorCodeMap.put(ErrorType.EXCEED_SESSION_INVOKE_LIMITED, "35");
        errorCodeMap.put(ErrorType.EXCEED_APP_INVOKE_LIMITED, "36");
        errorCodeMap.put(ErrorType.EXCEED_APP_INVOKE_FREQUENCY_LIMITED, "37");*/
    }

    public Error error() {
        return errorCodeMap.get(this);
    }

}

