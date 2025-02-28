package com.unibague.magno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class MagnoBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagnoBackApplication.class, args);
	}
}
