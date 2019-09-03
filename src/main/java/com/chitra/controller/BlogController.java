package com.chitra.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chitra.domain.Blog;
import com.chitra.repository.BlogRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/blog")
public class BlogController {
	
	@Autowired
	private BlogRepository blogRepository;
	
	@GetMapping
	public String showBlogItems(Model model) {
		List<Blog> allBlogItems = blogRepository.findAll();
		List<String> uniqueTopics = blogRepository.findAll()
										.stream()
										.map(Blog::getTopic)
										.distinct()
										.collect(Collectors.toList());
		log.debug("Created items");
		model.addAttribute("allBlogs", allBlogItems);
		model.addAttribute("uniqueTopics", uniqueTopics);
		return "blog";
	}
	
	@GetMapping("/new")
	public String createNewBlogEntry(Model model) {
		model.addAttribute("blog", new Blog());
		return "newBlog";
	}
	
	@PostMapping
	public String submitNewBlogEntry(@ModelAttribute Blog blog) {
		blogRepository.save(blog);
		return "redirect:/blog";
	}
}
