package com.ht.lc.dcp.common.http;

import com.ht.lc.dcp.common.config.SystemConfig;
import com.ht.lc.dcp.common.context.SpringContextManager;
import com.ht.lc.dcp.common.crypto.CipherServiceManager;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-14 15:51
 * @Version 1.0
 **/

public class HttpClientManager {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientManager.class);

    private static HttpClientManager INSTANCE = new HttpClientManager();

    private SystemConfig.HttpConfig httpConfig;

    private CloseableHttpClient syncHttpClient;

    public HttpClientManager () {
        this.httpConfig = SpringContextManager.getInstance().getBean(SystemConfig.HttpConfig.class);
        this.syncHttpClient = createSyncHttpClient();
    }

    public static HttpClientManager getInstance() {
        return INSTANCE;
    }

    private CloseableHttpClient createSyncHttpClient() {

        LOG.info("pool max: "+ httpConfig.getPoolMaxConnTotal()
                + "pool max route: " + httpConfig.getPoolMaxConnPerRoute()
                + "request con to: " + httpConfig.getReqConnTimeout()
                + "rsp to: " + httpConfig.getRspTimeout()
                + "req con request to: " + httpConfig.getReqConnRequestTimeout());

        SyncHttpClientBuilder syncHttpClientBuilder =  new SyncHttpClientBuilder();
        syncHttpClientBuilder.setPoolMaxConnPerRoute(httpConfig.getPoolMaxConnPerRoute());
        syncHttpClientBuilder.setPoolMaxConnTotal(httpConfig.getPoolMaxConnTotal());
        return syncHttpClientBuilder.build();
    }

    public void test() {
        LOG.info("hahahahha, enter !");
    }
}
