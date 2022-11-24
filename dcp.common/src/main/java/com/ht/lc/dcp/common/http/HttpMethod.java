package com.ht.lc.dcp.common.http;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-16 15:58
 * @Version 1.0
 **/
public enum HttpMethod {

    GET("GET"),

    POST("POST"),

    PUT("PUT"),

    DELETE("DELETE");

    private String name;

    private HttpMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
