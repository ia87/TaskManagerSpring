package com.my.taskmanagerspring.util;

import com.my.taskmanagerspring.interceptor.RequestData;
import com.my.taskmanagerspring.repository.UserGeneralRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(RepositorySwitcher.class);

    public RepositorySwitcher(@Qualifier("JPARepo") UserGeneralRepository JPARepo,
                              @Qualifier("JDBCRepo") UserGeneralRepository JDBCRepo) {
        this.JPARepo = JPARepo;
        this.JDBCRepo = JDBCRepo;
    }


    public UserGeneralRepository getRepository() {
        logger.debug("getRepository method where invoked");
        logger.debug("RequestData Bean Hashcode = " + requestData.hashCode() + " Data = " + requestData);
        UserGeneralRepository repo = JPARepo;
        while (requestData.getHeaderNames().hasMoreElements()) {
            String element = requestData.getHeaderNames().nextElement();
            if (element.equals("jdbc")) {
                repo = JDBCRepo;
                logger.debug("Found jdbc header");
                break;
            }
        }
        return repo;
    }
}
