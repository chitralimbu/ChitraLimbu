package com.chitra.api;

import java.util.List;
import java.util.stream.Collectors;

import com.chitra.resource.GitRepositoryResource;
import com.chitra.service.ConvertToResourceService;
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

	@Autowired
	private ConvertToResourceService convertService;

	@GetMapping(value = "/hal/full-refresh", produces = "application/hal+json")
	public ResponseEntity<List<GitRepositoryResource>> fullRefresh(RestTemplate restTemplate, Gson gson){
		List<GitRepository> allRepositories = gitRepoService
						.generateFinalGitRepository(restTemplate, gson, gitRepoService.fullRefreshGitRepository(restTemplate, gson));
		return new ResponseEntity<List<GitRepositoryResource>>(convertService.convertToGitRepoResourceList(allRepositories), HttpStatus.OK);
	}
	
	@GetMapping(produces = "application/hal+json")
	public ResponseEntity<List<GitRepositoryResource>> allRepository(){
		return new ResponseEntity<List<GitRepositoryResource>>(convertService.convertToGitRepoResourceList(gitRepo.findAll()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/hal/{repository}", produces = "application/hal+json")
	public ResponseEntity<GitRepositoryResource> getRepository(@PathVariable("repository") String repository) {
		return new ResponseEntity<GitRepositoryResource>(convertService.convertToGitRepoResource(gitRepo.findByName(repository)), HttpStatus.OK);
	}

	@GetMapping(value = "/hal/new/{repository}", produces = "application/hal+json")
	public ResponseEntity<GitRepositoryResource> addNewRepository(@PathVariable("repository") String repository, RestTemplate restTemplate, Gson gson){
		return new ResponseEntity<GitRepositoryResource>(convertService.convertToGitRepoResource(gitRepoService.updateRepository(repository, restTemplate, gson)), HttpStatus.CREATED);
	}

	@GetMapping(value = "/hal/refresh/{repository}", produces = "application/hal+json")
	public ResponseEntity<GitRepositoryResource> updateRepository(@PathVariable("repository") String repository, RestTemplate restTemplate, Gson gson) {
		return new ResponseEntity<GitRepositoryResource>(convertService.convertToGitRepoResource(gitRepoService.updateRepository(repository, restTemplate, gson)), HttpStatus.OK);
	}


}
