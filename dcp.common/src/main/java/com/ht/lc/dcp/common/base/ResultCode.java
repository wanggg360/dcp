package com.ht.lc.dcp.base;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-07-29 08:28
 * @Version 1.0
 **/
public enum ResultCode {

    SUCCESS(0, "SUCCESS"),
    SYS_ERROR(9999, "SYS_ERROR"),
    ;

    private int code;
    private String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    ResultCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
