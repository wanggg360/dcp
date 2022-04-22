package com.ht.lc.dcp.common.http;

import com.ht.lc.dcp.common.base.ResultCode;
import com.ht.lc.dcp.common.config.SystemConfig;
import com.ht.lc.dcp.common.context.SpringContextManager;
import com.ht.lc.dcp.common.exception.ServiceComException;
import com.ht.lc.dcp.common.utils.HttpClientUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

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

    private RequestConfig requestConfig;

    public HttpClientManager () {
        this.httpConfig = SpringContextManager.getInstance().getBean(SystemConfig.HttpConfig.class);
        this.requestConfig = HttpClientUtils.getDefaultRequestConfig(
                httpConfig.getReqConnTimeout(),
                httpConfig.getReqConnRequestTimeout(),
                httpConfig.getRspTimeout());
        this.syncHttpClient = SyncHttpClientBuilder.create()
                .setPoolMaxConnPerRoute(httpConfig.getPoolMaxConnPerRoute())
                .setPoolMaxConnTotal(httpConfig.getPoolMaxConnTotal())
                .build();
    }

    public static HttpClientManager getInstance() {
        return INSTANCE;
    }

    public  String doGet(String url, Map<String, String> headers, Map<String, String> params) throws ServiceComException {
        if (!CollectionUtils.isEmpty(params)){
            LOG.info("ready to build httpget parameters. ");
            url = getUrlWithParameters(url, params);
        }
        // 生成httpGet请求
        HttpGet req = (HttpGet)HttpClientUtils.getHttpRequest(url, HttpMethod.GET);
        req.setConfig(requestConfig);
        // 处理header
        if (!CollectionUtils.isEmpty(headers)) {
            LOG.info("ready to add headers. ");
            setHeaders4Request(headers, req);
        }
        return HttpClientUtils.getHttpResponseString(req, syncHttpClient);
    }

    private String getUrlWithParameters(String url, Map<String, String> params) {
        String result;
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(url).append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                        .append("&");
            }
            result =  sb.toString().substring(0, sb.length()-1);
        } catch (UnsupportedEncodingException e) {
            throw new ServiceComException(ResultCode.SYS_HTTP_ERROR.getCode(), "build httpget parameters error. ");
        }
        return result;
    }

    private void setHeaders4Request(Map<String, String> headers, HttpUriRequest req) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            req.addHeader(entry.getKey(), entry.getValue());
        }
    }
}
