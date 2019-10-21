package com.chitra.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chitra.domain.GitRepository;
import com.chitra.domain.GitRepositoryContents;
import com.chitra.repository.GitRepositoryRepository;
import com.chitra.service.CodeService;

@Controller
@RequestMapping("/code")
public class CodeController {
	
	@Autowired CodeService codeService;
	
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
		List<GitRepositoryContents> repoContents = codeService.turncatePath(repository.getAllContents());
		List<String> allHeadings = codeService.generateAllHeadings(repoContents).stream().distinct().collect(Collectors.toList());
		model.addAttribute("headings", allHeadings);
		model.addAttribute("repository", repository);
		model.addAttribute("repositoryContents", repoContents);
		return "code-topic";
	}
	
}
