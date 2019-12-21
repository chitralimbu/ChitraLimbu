package com.chitra.api;

import com.chitra.domain.GitRepository;
import com.chitra.repository.GitRepositoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchCodeController {

    @Autowired
    private GitRepositoryRepository gitRepo;

    @GetMapping
    public ResponseEntity<List<GitRepository>> searchCodeTitle(@RequestParam("keyword")Optional<String> keyword){
        List<GitRepository> allRepo = gitRepo.findAll();
        String searchBy = keyword.isPresent() ? keyword.get() : "invalid";
        if(!searchBy.equalsIgnoreCase("invalid")){
            allRepo = allRepo.stream()
                    .filter(repo ->  repo.getName().toLowerCase().contains(searchBy))
                    .collect(Collectors.toList());
        }
        return new ResponseEntity<>(allRepo, HttpStatus.OK);
    }
}
