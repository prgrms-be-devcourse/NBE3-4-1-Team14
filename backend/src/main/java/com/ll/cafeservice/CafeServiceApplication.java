package com.ll.cafeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CafeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeServiceApplication.class, args);
    }

}
