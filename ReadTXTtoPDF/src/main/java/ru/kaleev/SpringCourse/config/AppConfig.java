package ru.kaleev.SpringCourse.config;

import org.springframework.context.annotation.*;
import org.springframework.web.context.*;
import org.springframework.web.context.support.ServletContextResourceLoader;

import com.itextpdf.text.Image;

@Configuration
public class AppConfig {
    
    private final WebApplicationContext webApplicationContext;
    
    public AppConfig(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }
    
    @Bean
    public ServletContextResourceLoader servletContextResourceLoader() {
        return new ServletContextResourceLoader(webApplicationContext.getServletContext());
    }
    
}

