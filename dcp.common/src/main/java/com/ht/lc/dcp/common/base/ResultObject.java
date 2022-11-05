package com.ht.lc.dcp.common.base;

import java.io.Serializable;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-07-30 16:23
 * @Version 1.0
 **/
public class ResultObject<T> implements Serializable {

    boolean success;

    String code;

    String message;

    T data;

    public ResultObject() {}

    public ResultObject (boolean success, String code, String message, T data) {

        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static <T> ResultObject success(T data) {
        return  new ResultObject(true,
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getDesc(),
                data);
    }

    public static <T> ResultObject error(String code, String msg) {
        return  new ResultObject(false, code, msg, "");
    }

    public static <T> ResultObject error(ResultCode resultCode) {
        return new ResultObject(false, resultCode.getCode(), resultCode.getDesc(), "");
    }

}
