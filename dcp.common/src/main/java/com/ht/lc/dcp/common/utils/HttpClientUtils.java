package com.ht.lc.dcp.common.utils;

import com.ht.lc.dcp.common.constants.HttpConst;
import com.ht.lc.dcp.common.http.HttpMethod;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
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
        return RequestConfig.custom().setConnectTimeout(connTimeout, TimeUnit.SECONDS)
            .setConnectionRequestTimeout(connReqTimeout, TimeUnit.SECONDS)
            .setResponseTimeout(rspTimeout, TimeUnit.SECONDS).build();
    }

    // 信任所有的证书
    public static SSLContext getDefaultSSLContext() {

        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(new TrustStrategy() {
                @Override public boolean isTrusted(X509Certificate[] x509Certificates, String s)
                    throws CertificateException {
                    return true;
                }
            }).build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            LOG.error("init sslcontext error, can not load trust, exception: {}. ", e.getMessage());
        }
        return sslContext;
    }

    public static SSLConnectionSocketFactory getDefaultSSLSocketFactory(SSLContext sslContext) {
        return SSLConnectionSocketFactoryBuilder.create().setSslContext(sslContext)
            .setTlsVersions(TLS.V_1_0, TLS.V_1_1, TLS.V_1_2, TLS.V_1_3).build();
    }

    public static HttpClientConnectionManager getConnectManager(SSLConnectionSocketFactory sslcsf, int maxConnTotal,
        int maxConnPerRoute) {
        PoolingHttpClientConnectionManager pool = null;
        if (maxConnTotal <= 0 || maxConnPerRoute <= 0) {
            LOG.error("get http connection pool paramter wrong, use default config.");
            pool = PoolingHttpClientConnectionManagerBuilder.create().setSSLSocketFactory(sslcsf).build();
        } else {
            pool = PoolingHttpClientConnectionManagerBuilder.create().setSSLSocketFactory(sslcsf)
                .setMaxConnTotal(maxConnTotal).setMaxConnPerRoute(maxConnPerRoute).build();
        }
        return pool;
    }

    public static HttpClientContext getDefaultHttpClientContext() {
        return HttpClientContext.create();
    }

    public static HttpUriRequest getHttpRequest(final String url, HttpMethod m) {
        HttpUriRequest request = null;
        switch (m.getName()) {
            case "PUT":
                request = new HttpGet(url);
                break;
            case "POST":
                request = new HttpPost(url);
                break;
            case "GET":
                request = new HttpGet(url);
                break;
            case "DELETE":
                request = new HttpDelete(url);
                break;
            default:
                request = new HttpPost(url);
        }
        return request;
    }

    public static String getHttpResponseString(HttpUriRequest req, CloseableHttpClient client) {
        String response = "";
        final HttpClientResponseHandler<String> handler = new HttpClientResponseHandler<String>() {
            @Override public String handleResponse(final ClassicHttpResponse response) {
                String result = "";
                final int status = response.getCode();
                LOG.info("response status error, status code: {}. ", status);
                if (status >= HttpStatus.SC_SUCCESS && status < HttpStatus.SC_REDIRECTION) {
                    final HttpEntity entity = response.getEntity();
                    try {
                        result = entity != null ? EntityUtils.toString(entity, HttpConst.RSP_ENTITY_CHARSET) : "";
                    } catch (ParseException | IOException e) {
                        LOG.error("parse http response error, exception: {}. ", e.getMessage());
                    }
                }
                return result;
            }
        };

        try {
            response = client.execute(req, handler);
        } catch (IOException e) {
            LOG.error("request failed, url: {}, exception: {}. ", req.getRequestUri(), e.getMessage());
        }
        return response;
    }
}