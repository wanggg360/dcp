package com.ht.lc.dcp.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
@Configuration
@PropertySource("classpath:/conf/system.properties")
public class SystemConfig {

    @Configuration
    public static class KeyConfig {
        @Value("${aes.key}")
        private String aesKey;

        @Value("${aes.iv}")
        private String aesIv;

        public String getAesKey() {
            return aesKey;
        }

        public String getAesIv() {
            return aesIv;
        }
    }

    @Configuration
    public static class HttpConfig {
        @Value("${http.req.connect.timeout}")
        private int reqConnTimeout;

        @Value("${http.req.connect.request.timeout}")
        private int reqConnRequestTimeout;

        @Value("${http.rsp.timeout}")
        private int rspTimeout;

        @Value("${http.syncclient.pool.perroute.maxsize}")
        private int poolMaxConnPerRoute = 0;

        @Value("${http.syncclient.pool.maxsize}")
        private int poolMaxConnTotal = 0;

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
