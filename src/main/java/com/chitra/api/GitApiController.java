package com.chitra.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.chitra.domain.GitRepository;
import com.chitra.repository.GitRepositoryRepository;
import com.chitra.service.GitRepositoryService;
import com.google.gson.Gson;

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
	
	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public List<GitRepository> allRepository(){
		return gitRepo.findAll();
	}
	
	@GetMapping("/{repository}")
	@ResponseStatus(HttpStatus.OK)
	public GitRepository getRepository(@PathVariable("repository") String repository) {
		return gitRepo.findByName(repository);
	}
	
	@GetMapping("/refresh/{repository}")
	@ResponseStatus(HttpStatus.OK)
	public GitRepository updateRepository(@PathVariable("repository") String repository, RestTemplate restTemplate, Gson gson) {
		return gitRepoService.updateRepository(repository, restTemplate, gson);
	}
}
