package com.my.taskmanagerspring.util;

import com.my.taskmanagerspring.interceptor.RequestData;
import com.my.taskmanagerspring.repository.UserGeneralRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Data
public class RepositorySwitcher {
    private final UserGeneralRepository JPARepo;
    private final UserGeneralRepository JDBCRepo;
    @Autowired
    private RequestData requestData;

    public RepositorySwitcher(@Qualifier("JPARepo") UserGeneralRepository JPARepo,
                              @Qualifier("JDBCRepo") UserGeneralRepository JDBCRepo) {
        this.JPARepo = JPARepo;
        this.JDBCRepo = JDBCRepo;
    }


    public UserGeneralRepository getRepository() {
        System.out.println(requestData);
        System.out.println(requestData.hashCode());
//        String repo = "JPA";
        UserGeneralRepository repo = JPARepo;
        while (requestData.getHeaderNames().hasMoreElements()) {
            if (requestData.getHeaderNames().nextElement().equals("jdbc")) {
                repo = JDBCRepo;
            }
        }
        return repo;
    }
}
