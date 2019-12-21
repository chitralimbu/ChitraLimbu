package com.chitra.service;

import com.chitra.domain.GitRepository;
import com.chitra.repository.GitRepositoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SearchService {

    @Autowired
    private GitRepositoryRepository gitRepo;

    public Set<GitRepository> findInNameByKeyword(String keyword){
        return gitRepo.findAll().stream()
                .filter(repo ->  repo.getName().toLowerCase().contains(keyword))
                .collect(Collectors.toSet());
    }

    public Set<GitRepository> findInDescriptionByKeyword(String keyword){
        return gitRepo.findAll().stream()
                .filter(repo -> repo.getDescription().toLowerCase().contains(keyword))
                .collect(Collectors.toSet());
    }

    public Set<GitRepository> findInDescriptionAndName(String keyword, String description){
        return Stream.of(findInNameByKeyword(keyword), findInDescriptionByKeyword(description)).flatMap(Collection::stream).collect(Collectors.toSet());
    }
}
