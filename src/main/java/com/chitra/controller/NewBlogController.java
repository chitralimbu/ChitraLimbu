package com.chitra.controller;

import java.io.File;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
	public String submitNewBlogEntry(@RequestParam("file") MultipartFile file, @ModelAttribute @Valid Blog blog, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			log.error("Errors in form");
			bindingResult.getAllErrors().stream().forEach(System.out::println);
			return "newBlog";
		}
		if(file != null && !file.isEmpty()) {
			try {
				file.transferTo(new File("C:\\Users\\limbu\\Documents\\workspace-sts-3.9.7.RELEASE\\ChitraLimbu\\src\\main\\resources\\static\\images\\"+file.getOriginalFilename()));
			}catch(Exception e) {
				throw new RuntimeException("Product Image saving failed", e);
			}
		}
		blogRepository.save(blog);
		return "redirect:/blog/";
	}
}
