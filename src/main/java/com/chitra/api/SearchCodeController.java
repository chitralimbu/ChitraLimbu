package com.chitra.api;

import com.chitra.controller.CodeController;
import com.chitra.domain.GitRepository;
import com.chitra.repository.GitRepositoryRepository;
import com.chitra.resource.GitRepositoryResource;
import com.chitra.service.SearchService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
@Slf4j
public class SearchCodeController {

    @Autowired
    private GitRepositoryRepository gitRepo;

    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<List<GitRepositoryResource>> searchCodeTitle(@RequestParam("keyword")Optional<String> keyword, @RequestParam("description")Optional<String> description, HttpServletRequest request){
        String searchBy = description.isPresent() ? description.get() : keyword.isPresent() ? keyword.get() : "invalid";
        Set<GitRepository> allRepo = new HashSet<>();
        if(description.isPresent() && keyword.isPresent()){
            allRepo = searchService.findInDescriptionAndName(keyword.get(), description.get());
        }else if(keyword.isPresent()){
            allRepo = searchService.findInNameByKeyword(keyword.get());
        }else if(description.isPresent()){
            allRepo = searchService.findInDescriptionByKeyword(description.get());
        }
        log.info(String.format(request.getRequestURL() + request.getQueryString()));
        return new ResponseEntity<List<GitRepositoryResource>>(allRepo.stream().map(GitRepositoryResource::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> searchCodeTitleTest(@RequestParam("keyword")Optional<String> keyword, @RequestParam("description")Optional<String> description, HttpServletRequest request, RestTemplate restTemplate, Gson gson){
        String requestURL = "http://localhost:8080/search?" + request.getQueryString();
        log.info(requestURL);
        String json = restTemplate.getForObject(requestURL, String.class);
        List<GitRepository> allGitRepo = gson.fromJson(json, new TypeToken<List<GitRepository>>() {}.getType());
        allGitRepo.stream().forEach(repo -> {
            log.info(String.format("Git repo fullname: %s", repo.getFull_name()));
            log.info(String.format("Git repo id: %s", repo.getId()));
        });
        log.info(String.format("Items in allGitRepo: %s ", allGitRepo.size()));
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
