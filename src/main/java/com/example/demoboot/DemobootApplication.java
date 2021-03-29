package com.example.demoboot;

import com.example.demoboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemobootApplication {
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(DemobootApplication.class, args);
    }

}
