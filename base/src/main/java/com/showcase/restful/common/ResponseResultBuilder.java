package com.showcase.restful.common;

import com.alibaba.fastjson.JSON;

/**
 * @author: lei hang
 * @date: 2016年03月01日
 * @description:
 */
public class ResponseResultBuilder {

    public final static String SUCCESS = ApiConstants.SUCCESS;


    private static ResponseResultBuilder instance = new ResponseResultBuilder();

    public static ResponseResultBuilder getInstance() {
        return instance;
    }

    /**
     * 返回成功信息 对象
     */
    public ResponseResult genSuccess(Object data) {
        return new ResponseResult(SUCCESS, "success", data);
    }

    /**
     * 返回成功信息 JSON串
     */
    public String genSuccess2Json(Object data) {
        return JSON.toJSONString(genSuccess(data));
    }


    /**
     * 参数无效信息 对象
     */
    public ResponseResult genParamInvalid(String msg, Object data) {
        return new ResponseResult(ApiConstants.PARAM_INVALID, msg, data);
    }

    /**
     * 参数无效信息 JSON串
     */
    public String genParamInvalid2Json(String msg, Object data) {
        return JSON.toJSONString(genParamInvalid(msg, data));
    }

    /**
     * 返回服务信息 对象
     */
    public ResponseResult genRespsonResult(String code, String msg, Object data) {
        return new ResponseResult(code, msg, data);
    }

    /**
     * 返回服务信息 JSON串
     */
    public String genRespsonResult2Json(String code, String msg, Object data) {
        return JSON.toJSONString(genRespsonResult(code, msg, data));
    }

    /**
     * 系统运行异常信息 对象
     */
    public ResponseResult genUnknowError(Exception e) {
        return new ResponseResult(ApiConstants.UNKNOWN_ERROR,
                "system problem," + "please contact administrator!",
                e != null ? e.getMessage() : null);
    }

    /**
     * 系统运行异常信息 JSON串
     */
    public String genUnknowError2Json(Exception e) {
        return JSON.toJSONString(genUnknowError(e));
    }


}
