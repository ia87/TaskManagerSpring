package com.my.taskmanagerspring;

import com.my.taskmanagerspring.config.DataSourceJDBC;
import com.my.taskmanagerspring.dao.UserDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataSourceJDBC.class);
//        Arrays.stream(context.getBeanDefinitionNames())
//                .filter(s -> s.contains("DAO"))
//                .forEach(System.out::println);
//        UserDAO userDAO = context.getBean(UserDAO.class);
//        userDAO.getAllUsers(PageRequest.of(0, 10)).forEach(System.out::println);
//
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasenames("lang/res");
//        messageSource.setCacheSeconds(3600);
//        messageSource.setDefaultEncoding("UTF-8");
//        System.out.println(messageSource.getMessage("hello", null, Locale.ITALIAN));
    }

}

//todo todo-update save button language
//todo statuses
//todo user-edit page
//todo user-delete page
//todo sorting & filtering
//todo diagrame with finished/unfinished tasks on home-page
