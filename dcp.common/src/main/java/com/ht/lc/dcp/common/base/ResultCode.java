package com.ht.lc.dcp.common.base;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-07-29 08:28
 * @Version 1.0
 **/
public enum ResultCode {
    /**
     * 错误码6位：前两位代表大类 99（系统错误）
     *
     */

    SUCCESS(0, "SUCCESS"),
    SYS_INNER_ERROR(999999, "system inner error."),
    SYS_CIPHER_ERROR(991001, "system cipher error."),

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
