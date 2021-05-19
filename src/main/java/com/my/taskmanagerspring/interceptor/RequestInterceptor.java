package com.my.taskmanagerspring.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestInterceptor implements HandlerInterceptor {
    @Autowired
    RequestData requestData;

    Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        requestData.setHeaderNames(request.getHeaderNames());
        logger.debug("request URI = " + request.getRequestURI());
        logger.debug("headers applied : " + requestData.getHeaderNames());
        return true;
    }
}
