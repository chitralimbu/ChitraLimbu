package com.chitra;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

@SpringBootApplication
@EnableCaching
public class ChitraLimbuApplication {

	private static final String username = "chitralimbu";
	private static final String password = "Kitkat150!";
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.basicAuthentication(username, password).build();
	}
	
	@Bean
	public JsonParser jsonParser() {
		return new JsonParser();
	}
	
	@Bean 
	public Gson gson() {
		return new Gson();
	}
	
	@Bean 
	public Gson prettyPrint() {
		return new GsonBuilder().setPrettyPrinting().create();
	}
	
	@Bean 
	public CommandLineRunner run(RestTemplate restTemplate, JsonParser jsonParser, Gson gson, Gson prettyPrint) throws Exception{
		return args -> {
			
		};
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ChitraLimbuApplication.class, args);
	}
}
