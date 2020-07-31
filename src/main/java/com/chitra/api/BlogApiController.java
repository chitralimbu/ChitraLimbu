package com.chitra.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chitra.domain.Blog;
import com.chitra.repository.BlogRepository;

@RestController
@RequestMapping("/api")
public class BlogApiController {
	
	@Autowired
	private BlogRepository repository;
	
	@PostMapping("/addBlog")
	@ResponseStatus(HttpStatus.CREATED)
	public Blog saveBook(@RequestBody Blog blog) {
		return repository.save(blog);
	}
	
	@GetMapping("/blogs")
	public ResponseEntity<List<Blog>> findAllBlogs() {
		return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
	}
}
