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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
@Slf4j
public class SearchCodeController {

    @Autowired
    private GitRepositoryRepository gitRepo;

    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<Set<GitRepository>> searchCodeTitle(@RequestParam("keyword")Optional<String> keyword, @RequestParam("description")Optional<String> description, HttpServletRequest request) {
        String searchBy = description.isPresent() ? description.get() : keyword.isPresent() ? keyword.get() : "invalid";
        Set<GitRepository> allRepo = new HashSet<>();
        if (description.isPresent() && keyword.isPresent()) {
            allRepo = searchService.findInDescriptionAndName(keyword.get(), description.get());
        } else if (keyword.isPresent()) {
            allRepo = searchService.findInNameByKeyword(keyword.get());
        } else if (description.isPresent()) {
            allRepo = searchService.findInDescriptionByKeyword(description.get());
        }
        log.info(String.format(request.getRequestURL() + request.getQueryString()));
        return new ResponseEntity<Set<GitRepository>>(allRepo, HttpStatus.OK);
    }


    @GetMapping("/test/{search}")
    public ResponseEntity<List<GitRepository>> searchResults(@PathVariable("search") String search){
        long start = System.currentTimeMillis();
        log.info("Searching for term: " + search);
        List<GitRepository> foundRepo = gitRepo.findByNameContainingIgnoreCase(search);
        long end = System.currentTimeMillis() - start;
        log.info(String.format("Searching for %s took %d", search, end));
        return new ResponseEntity<>(foundRepo, HttpStatus.OK);
    }
}
