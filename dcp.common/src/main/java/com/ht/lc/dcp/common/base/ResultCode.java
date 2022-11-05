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
     * 返回码6位：前两位代表大类 99（系统错误）
     * 前两位10（鉴权错误）
     * 前两位
     *
     */

    SUCCESS("0", "success"),

    USER_NOT_EXIST("101001", "user not exist. "),
    USER_LOGIN_FAILED("101002", "userid or passwd wrong. "),
    USER_ALREADY_EXIST("101003", "user already exist. "),

    SYS_REQ_PARAM_ERROR("991001", "request param error. "),
    SYS_INNER_ERROR("999999", "system inner error. ")
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
