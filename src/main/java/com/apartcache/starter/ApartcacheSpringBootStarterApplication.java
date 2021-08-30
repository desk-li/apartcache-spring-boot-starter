package com.apartcache.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@EnableWebMvc
@SpringBootApplication
public class ApartcacheSpringBootStarterApplication {

    public static void main(String[] args) {
//        DispatcherServlet
//        AbstractHandlerMethodMapping
//        RequestMappingHandlerMapping
        SpringApplication.run(ApartcacheSpringBootStarterApplication.class, args);
    }

}
