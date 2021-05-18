package com.my.taskmanagerspring.util;

import com.my.taskmanagerspring.interceptor.RequestData;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class RepositorySwitcher {
    @Autowired
    private RequestData requestData;

    public String getRepository() {
        System.out.println(requestData);
        System.out.println(requestData.hashCode());
        String repo = "JPA";
        while (requestData.getHeaderNames().hasMoreElements()) {
            if (requestData.getHeaderNames().nextElement().equals("jdbc")) {
                repo = "JDBC";
            }
        }
        return repo;
    }
}
