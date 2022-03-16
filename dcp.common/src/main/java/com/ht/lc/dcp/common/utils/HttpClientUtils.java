package com.ht.lc.dcp.common.utils;

import com.ht.lc.dcp.common.base.ResultCode;
import com.ht.lc.dcp.common.exception.ServiceComException;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-11 19:21
 * @Version 1.0
 **/
public class HttpClientUtils {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtils.class);

    private HttpClientUtils() {
    }

    public static RequestConfig getDefaultRequestConfig(int connTimeout, int connReqTimeout, int rspTimeout) {
        return RequestConfig.custom()
                .setConnectTimeout(connTimeout, TimeUnit.SECONDS)
                .setConnectionRequestTimeout(connReqTimeout, TimeUnit.SECONDS)
                .setResponseTimeout(rspTimeout, TimeUnit.SECONDS)
                .build();
    }

    // 信任所有的证书
    public static SSLContext getDefaultSSLContext() {

        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            }).build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new ServiceComException(ResultCode.SYS_INNER_ERROR.getCode(), "init sslcontext error, can not load trust.", e);
        }
        return sslContext;
    }

    public static SSLConnectionSocketFactory getDefaultSSLSocketFactory(SSLContext sslContext) {
        return SSLConnectionSocketFactoryBuilder.create()
                .setSslContext(sslContext)
                .setTlsVersions(TLS.V_1_0, TLS.V_1_1, TLS.V_1_2, TLS.V_1_3)
                .build();
    }

    public static HttpClientConnectionManager getConnectManager(SSLConnectionSocketFactory sslcsf, int maxConnTotal, int maxConnPerRoute) {
        PoolingHttpClientConnectionManager pool = null;
        if (maxConnTotal <= 0 || maxConnPerRoute <= 0) {
            LOG.error("get http connection pool paramter wrong, use default config.");
            pool = PoolingHttpClientConnectionManagerBuilder.create()
                    .setSSLSocketFactory(sslcsf)
                    .build();
        } else {
            pool = PoolingHttpClientConnectionManagerBuilder.create()
                    .setSSLSocketFactory(sslcsf)
                    .setMaxConnTotal(maxConnTotal)
                    .setMaxConnPerRoute(maxConnPerRoute)
                    .build();
        }
        return pool;
    }

    public static HttpClientContext getDefaultHttpClientContext() {
        return HttpClientContext.create();
    }
}