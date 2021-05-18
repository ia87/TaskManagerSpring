package com.my.taskmanagerspring.interceptor;

import lombok.Data;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Data
public class RequestData implements HandlerInterceptor {
    private Enumeration<String> headerNames;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        headerNames = request.getHeaderNames();
//        System.out.println("Headers added");
//        System.out.println(headerNames);
//        System.out.println(this.hashCode());
        return true;
    }
}
