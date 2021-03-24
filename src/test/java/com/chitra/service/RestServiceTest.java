package com.chitra.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestServiceTest {

    @Value("${git_api}")
    private String git_url;

    @Autowired
    private RestService restService;

    @Test
    public void gitExchangeTest(){
        ResponseEntity<String> responseEntity = restService.gitExchange(git_url);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String body = responseEntity.getBody();
        assertTrue(body != null);
        assertFalse(body.isEmpty());
        log.info("body");
    }

}
