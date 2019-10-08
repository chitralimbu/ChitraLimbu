package com.chitra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
public class LayoutDialectConfig {
	@Bean 
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}
}
