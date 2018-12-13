package com.pollra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ComponentScan("com.pollra.*")
@SpringBootApplication
public class PollraWebserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(PollraWebserverApplication.class, args);
    }
}
