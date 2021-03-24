package com.chitra.service;

import com.chitra.domain.GitRepository;
import com.chitra.domain.GitRepositoryRecursive;
import com.chitra.repository.GitRepositoryRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GitRepositoryServiceTest {

    private static final String SFTP_REPO = "JSSH";

    @Autowired
    private GitRepositoryService gitRepositoryService;

    @Autowired
    private GitRepositoryRepository gitRepositoryRepository;

    private Gson prettyPrint = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void updateRepositoryTest(){
        GitRepository repository = gitRepositoryService.updateRepository(SFTP_REPO, new RestTemplate(), prettyPrint);
        assertTrue(repository != null);
        log.info("Repository returned: " + prettyPrint.toJson(repository));
    }

    @After
    public void cleanup(){
        gitRepositoryRepository.deleteAll();
    }
}
