package com.showcase.restful.api.common;

/**
 * @author: lei hang
 * @date: 2016年03月07日
 * @description:
 */

public class ApiConstants {
    /**
     * 版本号，包含在url请求路径内
     */
    public static final String API_VERSION = "v2";

    /**
     * 错误码
     */
    public static final String ERROR_CODE = "errorCode";


    /**
     * 错误代码定义：
     * 00：
     * 10：
     * 1001：
     * 90：未知错误
     */
    public final static String SUCCESS_STATUS = "00";


    //参数无效
    public final static String PARAM_INVALID = "10";

    //未知错误
    public final static String UNKNOWN_ERROR = "90";

    //系统错误
    public final static String SYSTEM_ERROR_STATUS = "99";
}
