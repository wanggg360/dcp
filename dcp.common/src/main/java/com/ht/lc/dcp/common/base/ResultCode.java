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
     * 前两位10（鉴权错误）
     *
     */

    SUCCESS("0", "success"),
    AUTH_USER_NOT_EXIST("101001", "user not exist. "),
    AUTH_WRONG_PASSWD("101002", "userid or passwd wrong. "),

    SYS_INNER_ERROR("999999", "system inner error."),
    SYS_CIPHER_ERROR("991001", "system cipher error."),
    SYS_HTTP_ERROR("991002", "system http error. ")
    ;

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    ResultCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
