package com.chitra.api;

import java.util.List;
import java.util.stream.Collectors;

import com.chitra.resource.GitRepositoryResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.chitra.domain.GitRepository;
import com.chitra.repository.GitRepositoryRepository;
import com.chitra.service.GitRepositoryService;
import com.google.gson.Gson;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/api/git")
public class GitApiController {
	
	@Autowired
	private GitRepositoryService gitRepoService;
	
	@Autowired
	private GitRepositoryRepository gitRepo;
	
	@GetMapping("/full-refresh")
	@ResponseStatus(HttpStatus.OK)
	public List<GitRepository> fullRefresh(RestTemplate restTemplate, Gson gson){
		List<GitRepository> allRepositories = gitRepoService.generateFinalGitRepository(restTemplate, gson, gitRepoService.fullRefreshGitRepository(restTemplate, gson));
		return allRepositories;
	}
	
	@GetMapping(produces = "application/hal+json")
	public ResponseEntity<List<GitRepositoryResource>> allRepository(){
		return new ResponseEntity<List<GitRepositoryResource>>(gitRepo.findAll().stream().map(GitRepositoryResource::new).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{repository}", produces = "application/hal+json")
	public ResponseEntity<GitRepositoryResource> getRepository(@PathVariable("repository") String repository) {
		return new ResponseEntity<GitRepositoryResource>(new GitRepositoryResource(gitRepo.findByName(repository)), HttpStatus.OK);
	}
	
	@PutMapping(value = "/refresh/{repository}", produces = "application/hal+json")
	@ResponseStatus(HttpStatus.OK)
	public GitRepository updateRepository(@PathVariable("repository") String repository, RestTemplate restTemplate, Gson gson) {
		return gitRepoService.updateRepository(repository, restTemplate, gson);
	}
}
