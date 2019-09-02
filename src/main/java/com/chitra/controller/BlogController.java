package com.chitra.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chitra.domain.Blog;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/blog")
public class BlogController {
	
	@GetMapping
	public String showBlogItems(Model model) {
		List<Blog> allBlogItems = Arrays.asList(
				new Blog(1, "first blog", "games", "My first blog body", "no image for this"),
				new Blog(2, "Second blog", "development", "development body", "has image for this"),
				new Blog(3, "third blog", "sport", "Sport blog body", "this has a sporty image")
				);
		
		model.addAttribute("allBlogs", allBlogItems);
		return "blog";
	}
	
}
