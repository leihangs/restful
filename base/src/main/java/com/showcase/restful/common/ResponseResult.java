package com.showcase.restful.common;

import java.io.Serializable;

/**
 * 接口返回数据结构
 */

public class ResponseResult implements Serializable{
    private String code;
    private String message;
    private Object data;

    public ResponseResult() {
    }

    public ResponseResult(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
