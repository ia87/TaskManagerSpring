package com.my.taskmanagerspring.config;

import com.my.taskmanagerspring.interceptor.RequestData;
import com.my.taskmanagerspring.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public static final String LANG = "lang";
    public static final String CLASSPATH_LANG_RES = "classpath:lang/res";
    public static final String UTF_8 = "UTF-8";
    public static final int CACHE_SECONDS = 3600;

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver slr = new CookieLocaleResolver();
//        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName(LANG);
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(getRequestInterceptor());
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(CLASSPATH_LANG_RES);
        messageSource.setCacheSeconds(CACHE_SECONDS);
        messageSource.setDefaultEncoding(UTF_8);
        return messageSource;
    }

    @Bean
    @RequestScope
    public RequestData requestData(){
        return new RequestData();
    }

    @Bean
    public RequestInterceptor getRequestInterceptor(){
        return new RequestInterceptor();
    }
}
