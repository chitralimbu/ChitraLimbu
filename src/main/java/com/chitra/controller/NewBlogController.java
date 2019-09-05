package com.chitra.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chitra.domain.Blog;
import com.chitra.repository.BlogRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/blog/new")
public class NewBlogController {
	
	@Autowired
	private BlogRepository blogRepository;
	
	@GetMapping
	public String createNewBlogEntry(Model model) {
		model.addAttribute("blog", new Blog());
		return "newBlog";
	}
	
	@PostMapping
	public String submitNewBlogEntry(@ModelAttribute @Valid Blog blog, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			log.error("Errors in form");
			return "newBlog";
		}
		
		blogRepository.save(blog);
		return "redirect:/blog/";
	}
}
