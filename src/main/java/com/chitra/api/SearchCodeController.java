package com.chitra.api;

import com.chitra.controller.CodeController;
import com.chitra.domain.GitRepository;
import com.chitra.repository.GitRepositoryRepository;
import com.chitra.resource.GitRepositoryResource;
import com.chitra.service.SearchService;
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

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchCodeController {

    @Autowired
    private GitRepositoryRepository gitRepo;

    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<List<GitRepositoryResource>> searchCodeTitle(@RequestParam("keyword")Optional<String> keyword, @RequestParam("description")Optional<String> description){
        String searchBy = description.isPresent() ? description.get() : keyword.isPresent() ? keyword.get() : "invalid";
        Set<GitRepository> allRepo = new HashSet<>();
        if(description.isPresent() && keyword.isPresent()){
            allRepo = searchService.findInDescriptionAndName(keyword.get(), description.get());
        }else if(keyword.isPresent()){
            allRepo = searchService.findInNameByKeyword(keyword.get());
        }else if(description.isPresent()){
            allRepo = searchService.findInDescriptionByKeyword(description.get());
        }
        return new ResponseEntity<List<GitRepositoryResource>>(allRepo.stream().map(GitRepositoryResource::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<List<GitRepositoryResource>> searchCodeTitleTest(@RequestParam("keyword")Optional<String> keyword, @RequestParam("description")Optional<String> description){
        String searchBy = description.isPresent() ? description.get() : keyword.isPresent() ? keyword.get() : "invalid";
        Set<GitRepository> allRepo = new HashSet<>();
        if(description.isPresent() && keyword.isPresent()){
            allRepo = searchService.findInDescriptionAndName(keyword.get(), description.get());
        }else if(keyword.isPresent()){
            allRepo = searchService.findInNameByKeyword(keyword.get());
        }else if(description.isPresent()){
            allRepo = searchService.findInDescriptionByKeyword(description.get());
        }
        return new ResponseEntity<List<GitRepositoryResource>>(allRepo.stream().map(GitRepositoryResource::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    //new GitRepositoryResource(gitRepo.findByName(keyword.get()))
}
