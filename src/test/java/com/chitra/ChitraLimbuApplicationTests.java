package com.chitra;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChitraLimbuApplicationTests {

	@Value("${git_api}")
	private String git_url;

	@Value("${git_token}")
	private String token;

	@Test
	public void contextLoads() {
	}

	@Test
	public void restTemplateTest(){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "token " + token);
		HttpEntity httpEntity = new HttpEntity(headers);
		ResponseEntity<String> response = restTemplate.exchange(git_url, HttpMethod.GET, httpEntity, String.class);
		String out = response.getBody();
		log.info("Out: " + out);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void restTemplateApiTest(){
		RestTemplate restTemplate = new RestTemplate();

		String out = restTemplate.getForObject("https://api.github.com/", String.class);
		log.info("Out:" + out);
	}

}
