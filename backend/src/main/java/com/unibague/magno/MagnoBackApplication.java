package com.unibague.magno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MagnoBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagnoBackApplication.class, args);
    }
}