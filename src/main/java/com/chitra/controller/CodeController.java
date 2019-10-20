package com.chitra.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chitra.domain.GitRepository;
import com.chitra.domain.GitRepositoryContents;
import com.chitra.domain.GitRepositoryRecursive;
import com.chitra.repository.GitRepositoryRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/code")
@Slf4j
public class CodeController {

	@Autowired
	private GitRepositoryRepository gitRepo;
	
	@GetMapping
	public String getCode(Model model) {
		model.addAttribute("gitRepo", gitRepo.findAll());
		return "code";
	}
	
	@GetMapping("/{name}")
	public String topicCode(Model model, @PathVariable("name") String name) {
		GitRepository repository = gitRepo.findByName(name);
		List<GitRepositoryContents> repoContents = turncatePath(repository.getAllContents());
		List<String> allHeadings = generateAllHeadings(repoContents);
		model.addAttribute("headings", allHeadings);
		model.addAttribute("repository", repository);
		model.addAttribute("repositoryContents", repoContents);
		return "code-topic";
	}
	
	private List<String> generateAllHeadings(List<GitRepositoryContents> repoContents){
		List<String> allHeadings = new ArrayList<>();
		for(GitRepositoryContents contents: repoContents) {
			if(contents.getType().equals("dir")) {
				for(GitRepositoryRecursive allRec: contents.getRecursive()) {
					String fullPath = String.format("%s",allRec.getPath());
					allHeadings.add(filterPath(fullPath));
				}
			}else {
				allHeadings.add(contents.getPath());
			}
			
		}
		return allHeadings;
	}
	
	private String filterPath(String fullPath) {
		List<String> fullPathSplit = Arrays.asList(fullPath.split("/"));
		return fullPathSplit.get(fullPathSplit.size() -1);
	}
	
	private List<GitRepositoryContents> turncatePath(List<GitRepositoryContents> gitRepositoryContents){
		for(GitRepositoryContents gitRepoContents: gitRepositoryContents) {
			List<GitRepositoryRecursive> listRecur = gitRepoContents.getRecursive();
			if(listRecur != null) {
				for(GitRepositoryRecursive recur : gitRepoContents.getRecursive()) {
					recur.setPath(filterPath(recur.getPath()));
				}
			}
		}
		return gitRepositoryContents;
	}
}
