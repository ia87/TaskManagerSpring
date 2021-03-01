package com.my.taskmanagerspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
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
