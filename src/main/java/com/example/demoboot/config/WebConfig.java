package com.example.demoboot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("com.example.demoboot")
public class WebConfig implements WebMvcConfigurer {
    @Override
   public void addViewControllers(ViewControllerRegistry registry) {
       registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/user").setViewName("user");
   }

}
