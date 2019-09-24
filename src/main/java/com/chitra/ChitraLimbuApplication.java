package com.chitra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ChitraLimbuApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChitraLimbuApplication.class, args);
	}

}
