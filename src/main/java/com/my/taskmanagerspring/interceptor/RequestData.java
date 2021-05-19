package com.my.taskmanagerspring.interceptor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Enumeration;

@Slf4j
@Data
public class RequestData implements HandlerInterceptor {
    private Enumeration<String> headerNames;
    Logger logger = LoggerFactory.getLogger(RequestData.class);

    public RequestData() {
      logger.debug("Bean created. Hashcode = " + this.hashCode());
      headerNames = Collections.emptyEnumeration();
    }
}
