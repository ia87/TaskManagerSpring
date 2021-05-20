package com.my.taskmanagerspring.interceptor;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Data
public class RequestData {
    private String repositorySwitch;
}
