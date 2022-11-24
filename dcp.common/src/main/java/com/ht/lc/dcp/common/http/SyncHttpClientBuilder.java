package com.ht.lc.dcp.common.http;

import com.ht.lc.dcp.common.utils.HttpClientUtils;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-11 19:28
 * @Version 1.0
 **/

public class SyncHttpClientBuilder {

    private int poolMaxConnTotal;

    private int poolMaxConnPerRoute;

    public SyncHttpClientBuilder() {
    }

    public static SyncHttpClientBuilder create() {
        return new SyncHttpClientBuilder();
    }

    public int getPoolMaxConnTotal() {
        return poolMaxConnTotal;
    }

    public SyncHttpClientBuilder setPoolMaxConnTotal(int poolMaxConnTotal) {
        this.poolMaxConnTotal = poolMaxConnTotal;
        return this;
    }

    public int getPoolMaxConnPerRoute() {
        return poolMaxConnPerRoute;
    }

    public SyncHttpClientBuilder setPoolMaxConnPerRoute(int poolMaxConnPerRoute) {
        this.poolMaxConnPerRoute = poolMaxConnPerRoute;
        return this;
    }

    // 获取同步客户端
    public CloseableHttpClient build() {

        return HttpClients.custom().setConnectionManager(HttpClientUtils
                .getConnectManager(HttpClientUtils.getDefaultSSLSocketFactory(HttpClientUtils.getDefaultSSLContext()),
                        poolMaxConnTotal, poolMaxConnPerRoute)).build();
    }
}
