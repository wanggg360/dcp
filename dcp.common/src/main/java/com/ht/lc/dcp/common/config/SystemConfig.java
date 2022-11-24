package com.ht.lc.dcp.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-09-10 08:36
 * @Version 1.0
 **/
@Configuration @PropertySource("classpath:/conf/system.properties") public class SystemConfig {

    @Configuration public static class KeyConfig {
        @Value("${aes.key}") private String aesKey;

        @Value("${aes.iv}") private String aesIv;

        public String getAesKey() {
            return aesKey;
        }

        public String getAesIv() {
            return aesIv;
        }
    }

    @Configuration public static class HttpConfig {
        @Value("${http.req.connect.timeout:180}") private int reqConnTimeout;

        @Value("${http.req.connect.request.timeout:180}") private int reqConnRequestTimeout;

        @Value("${http.rsp.timeout:60}") private int rspTimeout;

        @Value("${http.syncclient.pool.perroute.maxsize:5}") private int poolMaxConnPerRoute;

        @Value("${http.syncclient.pool.maxsize:20}") private int poolMaxConnTotal;

        public int getReqConnTimeout() {
            return reqConnTimeout;
        }

        public int getReqConnRequestTimeout() {
            return reqConnRequestTimeout;
        }

        public int getRspTimeout() {
            return rspTimeout;
        }

        public int getPoolMaxConnPerRoute() {
            return poolMaxConnPerRoute;
        }

        public int getPoolMaxConnTotal() {
            return poolMaxConnTotal;
        }
    }
}
