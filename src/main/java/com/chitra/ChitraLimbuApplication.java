package com.chitra;

import com.chitra.prometheus.HomePageHits;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@SpringBootApplication
@EnableCaching
@Slf4j
public class ChitraLimbuApplication {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
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
			/*log.debug(restTemplate.getForObject("https://api.github.com/rate_limit", String.class));*/
		};
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ChitraLimbuApplication.class, args);
	}
}
