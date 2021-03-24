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

	private List<String> ignoreList = Arrays.asList("favicon.ico","maven-wrapper.properties","MavenWrapperDownloader.java","pom.properties", ".adoc","gradle","MANIFEST.MF",".pyc",".mxl", ".DS_Store",".jpg", ".jpeg", ".png",".war", ".jar",".mvn/wrapper",".gitignore","mvnw","mvnw.cmd","docx",".classpath",".project",".settings","bin",".class",".mp3",".project");
	private static final String username="chitralimbu";
	private static final String GITHUB_REPOSITORY="https://api.github.com/users/chitralimbu/repos";
	private final RestService restService;
	private final GitRepositoryRepository gitRepo;
	private final GitRepositoryRecursiveRepository gitRecursiveRepo;
	private final GitRepositoryContentsRepository gitContentsRepo;

	public GitRepositoryService(GitRepositoryRepository gitRepo, GitRepositoryRecursiveRepository gitRecursiveRepo, GitRepositoryContentsRepository gitContentsRepo, RestService restService) {
		this.gitRepo = gitRepo;
		this.gitRecursiveRepo = gitRecursiveRepo;
		this.gitContentsRepo = gitContentsRepo;
		this.restService = restService;
	}

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
		log.info("Path of tree: " + parentOfTree);
		gitRepositoryRecursive.setRaw(generateRawURL(fullName, String.format("%s/%s", parentOfTree, gitRepositoryRecursive.getPath())));
		if(gitRepositoryRecursive.getCode() == null || gitRepositoryRecursive.getCode().equals("no code")) {
			log.info(String.format("Code for path %s is empty adding code if available", gitRepositoryRecursive.getPath()));
			String raw = gitRepositoryRecursive.getRaw();
			//restTemplate.getForObject(gitRepositoryRecursive.getRaw(), String.class)
			gitRepositoryRecursive.setCode(restService.gitExchange(gitRepositoryRecursive.getRaw()).getBody());
		}
		return gitRepositoryRecursive;
	}

	private List<GitRepositoryRecursive> generateRecursive(GitRepositoryContents gitContents, RestTemplate restTemplate, Gson gson){
		String getGitURL = String.format("%s?recursive=1", gitContents.getGit_url());
		log.info("Get git URL: " + getGitURL);
		//restTemplate.getForObject(), String.class);
		String gitContentsRecursive = restService.gitExchange(getGitURL).getBody();
		List<GitRepositoryRecursive> jobjRecursive = gson.fromJson(getElementFromJson(gitContentsRecursive, "tree"), new TypeToken<List<GitRepositoryRecursive>>() {}.getType());
		return jobjRecursive.stream()
				.filter(obj -> obj.getType().equals("blob"))
				.filter(obj -> !stringContains(obj.getPath()))
				.collect(Collectors.toList());
	}

	private List<GitRepositoryRecursive> addRawToGitRepositoryRecursive(List<GitRepositoryRecursive> gitReposRecursive, String fullName, String parentOfTree, RestTemplate restTemplate){
		gitReposRecursive.forEach(obj -> returnRecursiveRaw(obj, fullName, parentOfTree, restTemplate));
		log.debug(String.format("Successfully generated raw from tree data %s", gitReposRecursive));
		gitRecursiveRepo.saveAll(gitReposRecursive);
		log.info("Successfully saved Tree to database");
		return gitReposRecursive;
	}

	private List<GitRepositoryContents> generateFinalGitRepoContents(List<GitRepositoryContents> gitContents, GitRepository repository, RestTemplate restTemplate, Gson gson){
		for(GitRepositoryContents gr : gitContents) {
			if(gr.getType().equals("dir")) {
				log.debug(String.format("Content %s is a directory", gr));
				String fullName = repository.getFull_name();
				String parentOfTree = gr.getPath();
				//TODO
				List<GitRepositoryRecursive> jobjRecursive = addRawToGitRepositoryRecursive(generateRecursive(gr, restTemplate, gson), fullName, parentOfTree, restTemplate);
				gr.setRecursive(jobjRecursive);
				gr.setIgnore(true);
			}else if(gr.getType().equals("file")){
				log.debug(String.format("Content %s is a file", gr));
				String fileDownloadUrl = gr.getDownload_url();
				gr.setRaw(restService.gitExchange(fileDownloadUrl).getBody());
			}
		}
		log.debug(String.format("Successfully generated contents for git repository %s", gitContents));
		gitContentsRepo.saveAll(gitContents);
		log.info("Successfully saved contents to database");
		return gitContents;
	}

	public List<GitRepository> generateFinalGitRepository(RestTemplate restTemplate, Gson gson, List<GitRepository> allGitContents) {
		for(GitRepository gitRepository: allGitContents) {
			gitRepository = updateRepo(restTemplate, gson, gitRepository);
		}
		return allGitContents;
	}

	private GitRepository updateRepo(RestTemplate restTemplate, Gson gson, GitRepository gitRepository) {
		/*String contents = restTemplate.getForObject(getContentsURL(gitRepository.getFull_name()), String.class);*/
		String repositoryUrl = getContentsURL(gitRepository.getFull_name());
		log.info(String.format("Requesting api from url: %s", repositoryUrl));
		String contents = restService.gitExchange(repositoryUrl).getBody();
		List<GitRepositoryContents> gitContents = gson.fromJson(contents, new TypeToken<List<GitRepositoryContents>>() {}.getType());
		gitContents = gitContents.stream().filter(obj -> !stringContains(obj.getPath())).collect(Collectors.toList());
		gitContents = generateFinalGitRepoContents(gitContents, gitRepository, restTemplate, gson);
		gitRepository.setAllContents(gitContents);
		gitRepo.save(gitRepository);
		log.info(String.format("Successfully updated Repository %s", gitRepository.getName()));
		return gitRepository;
	}


	public GitRepository updateRepository(String repository, RestTemplate restTemplate, Gson gson) {
		log.info(String.format("Updating/adding repository %s/%s", username, repository));
		/*String thisRepository = restTemplate.getForObject(GITHUB_REPOSITORY, String.class);*/
		String thisRepository = restService.gitExchange(GITHUB_REPOSITORY).getBody();
		List<GitRepository> gitRepository = gson.fromJson(thisRepository, new TypeToken<List<GitRepository>>() {}.getType());
		GitRepository toUpdate = gitRepository.stream()
					.filter(repo -> repo.getName().contains(repository))
					.findAny()
					.orElseThrow(IllegalArgumentException::new);
		return updateRepo(restTemplate, gson, toUpdate); 
	}


	public List<GitRepository> fullRefreshGitRepository(RestTemplate restTemplate, Gson gson){
		log.info("Refreshing all repositories");
		String gitRepoContents = restTemplate.getForObject(GITHUB_REPOSITORY, String.class);
		List<GitRepository> allRepositories = gson.fromJson(gitRepoContents, new TypeToken<List<GitRepository>>() {}.getType()); 
		return generateFinalGitRepository(restTemplate, gson, allRepositories);
	}

}
