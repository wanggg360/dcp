package com.ht.lc.dcp.common.interceptor;

import com.ht.lc.dcp.common.constants.HttpConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InterfaceLogInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger("interfacelog");

    private static final String LOG_SEPARATOR = " | ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        long intime = System.currentTimeMillis();
        request.setAttribute(HttpConst.REQ_ATTR_START_TIME, intime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        long costtime = System.currentTimeMillis() - (long) request.getAttribute(HttpConst.REQ_ATTR_START_TIME);

        LOG.info(request.getRequestURI() + LOG_SEPARATOR + request.getMethod() + LOG_SEPARATOR + request.getProtocol()
                + LOG_SEPARATOR + costtime + LOG_SEPARATOR + response.getStatus() + LOG_SEPARATOR + request.getRemoteAddr()
                + LOG_SEPARATOR + request.getLocalAddr());
    }
}
