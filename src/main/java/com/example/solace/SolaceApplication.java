package com.example.solace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SolaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolaceApplication.class, args);
    }

}
