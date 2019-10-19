package com.chitra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chitra.repository.GitRepositoryRepository;

@Controller
@RequestMapping("/java")
public class JavaController {

	@Autowired
	private GitRepositoryRepository gitRepo;
	
	@GetMapping
	public String getJava(Model model) {
		model.addAttribute("gitRepo", gitRepo.findAll());
		return "java";
	}
	
}
