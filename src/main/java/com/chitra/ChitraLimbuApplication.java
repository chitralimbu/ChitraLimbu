package com.chitra;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.chitra.domain.GitDirTree;
import com.chitra.domain.GitObject;
import com.chitra.domain.Repository;
import com.chitra.repository.GitDirTreeRepository;
import com.chitra.repository.GitObjectRepository;
import com.chitra.repository.RepositoryRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableCaching
@Slf4j
public class ChitraLimbuApplication {

	@Autowired
	private RepositoryRepository repoRepo;

	@Autowired 
	private GitObjectRepository gitObjectRepo;


	@Autowired
	private GitDirTreeRepository treeRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(ChitraLimbuApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.basicAuthentication("chitralimbu", "Kitkat150!").build();
	}
	
	public String getContentsURL(String fullName) {
		return String.format("https://api.github.com/repos/%s/contents", fullName);
	}

	public String generateRawURL(String fullName, String path) {
		return String.format("https://raw.githubusercontent.com/%s/master/%s", fullName, path);
	}
	
	@Bean 
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception{
		return args -> {

			Gson gson = new Gson();
			Gson prettyPrint = new GsonBuilder().setPrettyPrinting().create();
			
			Repository gitRepo = repoRepo.findByName("webstore");
			String gitRepoPretty = prettyPrint.toJson(gitRepo);
			//System.out.println(gitRepoPretty);
			
			String fullName = gitRepo.getFull_name();
			List<GitObject> gitObjects = gitRepo.getAllItems()
											.stream()
											.filter(obj -> obj.getType().equals("dir"))
											.collect(Collectors.toList());
			for(GitObject gdt : gitObjects) {
				String root = gdt.getName();
				List<GitDirTree> gitTree = gdt.getGitDirTree()
											.stream()
											.filter(obj -> obj.getType().equals("blob"))
											.filter(obj -> !obj.getPath().endsWith(".class"))
											.collect(Collectors.toList());
				gitTree.forEach(obj -> System.out.println(generateRawURL(fullName, String.format("%s/%s",root,obj.getPath()))));
			}
			gitRepo.getAllItems()
				.stream()
				.filter(gitObj -> gitObj.getType().equals("dir"))
				.forEach(a -> {
					
				});
		};
	}

}
