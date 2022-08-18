package com.ht.lc.dcp.common.exception;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-10-20 10:31
 * @Version 1.0
 **/
public class ServiceException extends RuntimeException {

    private String code;

    private String msg;

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public ServiceException(String code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }
}
