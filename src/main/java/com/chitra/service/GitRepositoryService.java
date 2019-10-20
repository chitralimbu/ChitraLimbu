package com.chitra.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.chitra.domain.GitRepository;
import com.chitra.domain.GitRepositoryContents;
import com.chitra.domain.GitRepositoryRecursive;
import com.chitra.repository.GitRepositoryContentsRepository;
import com.chitra.repository.GitRepositoryRecursiveRepository;
import com.chitra.repository.GitRepositoryRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GitRepositoryService {
	
	private List<String> ignoreList = Arrays.asList(".pyc",".mxl", ".DS_Store",".jpg", ".jpeg", ".png",".war", ".jar",".mvn/wrapper",".gitignore","mvnw","mvnw.cmd","docx",".classpath",".project",".settings","bin",".class",".mp3",".project");

	@Autowired
	private GitRepositoryRepository gitRepo;
	
	@Autowired
	private GitRepositoryRecursiveRepository gitRecursiveRepo;
	
	@Autowired
	private GitRepositoryContentsRepository gitContentsRepo;
	
	public String getContentsURL(String fullName) {
		return String.format("https://api.github.com/repos/%s/contents", fullName);
	}

	public String generateRawURL(String fullName, String path) {
		return String.format("https://raw.githubusercontent.com/%s/master/%s", fullName, path);
	}	
	
	public boolean stringContains(String path) {
		for(String ignore: ignoreList) {
			if(path.contains(ignore)) {
				return true;
			}
		}
		return false;
	}
	
	public String getElementFromJson(String json, String element) {
		Gson gson = new Gson();
		JsonObject jobj = gson.fromJson(json, JsonObject.class);
		return jobj.get(element).toString();
	}
	
	private  GitRepositoryRecursive returnRecursiveRaw(GitRepositoryRecursive gitRepositoryRecursive, String fullName, String parentOfTree, RestTemplate restTemplate) {
		gitRepositoryRecursive.setRaw(generateRawURL(fullName, String.format("%s/%s", parentOfTree, gitRepositoryRecursive.getPath())));
		//gitRepositoryRecursive.setCode(restTemplate.getForObject(gitRepositoryRecursive.getRaw(), String.class));
		if(gitRepositoryRecursive.getCode() == null || gitRepositoryRecursive.getCode().equals("no code")) {
			log.info(String.format("Code for path %s is empty adding code if available", gitRepositoryRecursive.getPath()));
			gitRepositoryRecursive.setCode(restTemplate.getForObject(gitRepositoryRecursive.getRaw(), String.class));
		}
		return gitRepositoryRecursive;
	}
	
	private List<GitRepositoryRecursive> generateRecursive(GitRepositoryContents gitContents, RestTemplate restTemplate, Gson gson){
		String gitContentsRecursive = restTemplate.getForObject(String.format("%s?recursive=1", gitContents.getGit_url()), String.class);
		List<GitRepositoryRecursive> jobjRecursive = gson.fromJson(getElementFromJson(gitContentsRecursive, "tree"), new TypeToken<List<GitRepositoryRecursive>>() {}.getType());
		return jobjRecursive.stream()
				.filter(obj -> obj.getType().equals("blob"))
				.filter(obj -> !stringContains(obj.getPath()))
				.collect(Collectors.toList());
	}
	
	private List<GitRepositoryRecursive> addRawToGitRepositoryRecursive(List<GitRepositoryRecursive> gitReposRecursive, String fullName, String parentOfTree, RestTemplate restTemplate){
		gitReposRecursive.forEach(obj -> returnRecursiveRaw(obj, fullName, parentOfTree, restTemplate));
		log.info(String.format("Successfully generated raw from tree data %s", gitReposRecursive));
		gitRecursiveRepo.saveAll(gitReposRecursive);
		log.info("Successfully saved Tree to database");
		return gitReposRecursive;
	}
	
	private List<GitRepositoryContents> generateFinalGitRepoContents(List<GitRepositoryContents> gitContents, GitRepository repository, RestTemplate restTemplate, Gson gson){
		for(GitRepositoryContents gr : gitContents) {
			if(gr.getType().equals("dir")) {
				String fullName = repository.getFull_name();
				String parentOfTree = gr.getPath();
				List<GitRepositoryRecursive> jobjRecursive = addRawToGitRepositoryRecursive(generateRecursive(gr, restTemplate, gson), fullName, parentOfTree, restTemplate);
				gr.setRecursive(jobjRecursive);
			}
		}
		log.info(String.format("Successfully generated contents for git repository %s", gitContents));
		gitContentsRepo.saveAll(gitContents);
		log.info("Successfully saved contents to database");
		return gitContents;
	}
	
	public List<GitRepository> generateFinalGitRepository(RestTemplate restTemplate, Gson gson, List<GitRepository> allGitContents) {
		for(GitRepository gitRepository: allGitContents) {
			String contents = restTemplate.getForObject(getContentsURL(gitRepository.getFull_name()), String.class);
			List<GitRepositoryContents> gitContents = gson.fromJson(contents, new TypeToken<List<GitRepositoryContents>>() {}.getType());
			gitContents = gitContents.stream().filter(obj -> !stringContains(obj.getPath())).collect(Collectors.toList());
			gitContents = generateFinalGitRepoContents(gitContents, gitRepository, restTemplate, gson);
			gitRepository.setAllContents(gitContents);
		}
		log.info(String.format("Successfully generated final contents for git repository %s", allGitContents));
		gitRepo.saveAll(allGitContents);
		log.info("Successfuly saved repository to database");
		return allGitContents;
	}
	
	public List<GitRepository> fullRefreshGitRepository(RestTemplate restTemplate, Gson gson){
		log.info("Calling GitHub api");
		String gitRepoContents = restTemplate.getForObject("https://api.github.com/users/chitralimbu/repos", String.class);
		return gson.fromJson(gitRepoContents, new TypeToken<List<GitRepository>>() {}.getType());
	}
	
}
