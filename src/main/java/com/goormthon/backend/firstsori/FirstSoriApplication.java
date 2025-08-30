package com.goormthon.backend.firstsori;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FirstSoriApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstSoriApplication.class, args);
    }

}
