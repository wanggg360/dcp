package com.ht.lc.dcp.common.exception;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-10-20 10:31
 * @Version 1.0
 **/
public class ServiceComException extends RuntimeException {

    private int code;

    private String msg;

    public ServiceComException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public ServiceComException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }
}
